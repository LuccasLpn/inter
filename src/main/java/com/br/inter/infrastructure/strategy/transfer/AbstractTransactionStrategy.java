package com.br.inter.infrastructure.strategy.transfer;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.gateway.ExchangeRateGateway;
import com.br.inter.domain.transaction.model.ExchangeRate;
import com.br.inter.infrastructure.exceptions.DailyLimitExceededException;
import com.br.inter.infrastructure.exceptions.InsufficientFundsException;
import com.br.inter.infrastructure.exceptions.SenderWalletNotFoundException;
import com.br.inter.infrastructure.repository.TransactionRepository;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.ExchangeRateEntity;
import com.br.inter.infrastructure.repository.entity.TransactionEntity;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.repository.enums.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractTransactionStrategy implements TransactionStrategy {

    protected final TransactionRepository transactionRepository;
    protected final WalletRepository walletRepository;
    protected final ExchangeRateGateway exchangeRateGateway;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public RemittanceResponse processTransaction(RemittanceRequest remittanceRequest) {
        WalletEntity senderWallet = findSenderWallet(remittanceRequest.getFromWalletUuid());
        validateBalance(senderWallet, remittanceRequest.getAmount());
        validateDailyLimit(senderWallet, remittanceRequest.getAmount());
        ExchangeRate exchangeRate = exchangeRateGateway.fetchLatestExchangeRate();
        BigDecimal convertedAmount = convertCurrency(remittanceRequest.getAmount(), exchangeRate.getRate());
        WalletEntity receiverWallet = findReceiverWallet(remittanceRequest, senderWallet);
        TransactionEntity transaction = executeTransaction(
                senderWallet,
                receiverWallet,
                remittanceRequest.getAmount(),
                convertedAmount,
                new ExchangeRateEntity(exchangeRate));
        return new RemittanceResponse(
                transaction.getUuid().toString(),
                senderWallet.getUuid().toString(),
                receiverWallet.getUuid().toString(),
                remittanceRequest.getAmount(),
                convertedAmount,
                exchangeRate.getRate(),
                "COMPLETED",
                LocalDateTime.now()
        );
    }

    protected WalletEntity findSenderWallet(String fromWalletUuid) {
        return walletRepository.findByUuid(fromWalletUuid)
                .orElseThrow(() -> new SenderWalletNotFoundException("Sender wallet not found"));
    }

    protected void validateBalance(WalletEntity wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }

    protected void validateDailyLimit(WalletEntity wallet, BigDecimal amount) {
        BigDecimal dailyLimit = wallet.getUserEntity().getTransactionLimitEntity().getDailyLimit();
        BigDecimal usedToday = wallet.getUserEntity().getTransactionLimitEntity().getUsedToday();
        if (usedToday.add(amount).compareTo(dailyLimit) > 0) {
            throw new DailyLimitExceededException("Daily transaction limit exceeded");
        }
        wallet.getUserEntity().getTransactionLimitEntity().setUsedToday(usedToday.add(amount));
    }

    protected BigDecimal convertCurrency(BigDecimal amount, BigDecimal exchangeRate) {
        return amount.divide(exchangeRate, 2, RoundingMode.HALF_UP);
    }

    protected abstract WalletEntity findReceiverWallet(RemittanceRequest remittanceRequest, WalletEntity senderWallet);

    protected TransactionEntity executeTransaction(WalletEntity senderWallet, WalletEntity receiverWallet, BigDecimal amount, BigDecimal convertedAmount, ExchangeRateEntity exchangeRate) {
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(convertedAmount));
        TransactionEntity transaction = new TransactionEntity();
        transaction.setUuid(UUID.randomUUID());
        transaction.setFromWalletEntity(senderWallet);
        transaction.setToWalletEntity(receiverWallet);
        transaction.setAmount(amount);
        transaction.setExchangeRateEntity(exchangeRate);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionType(getTransactionType());
        transactionRepository.save(transaction);
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
        return transaction;
    }

    protected abstract TransactionType getTransactionType();
}