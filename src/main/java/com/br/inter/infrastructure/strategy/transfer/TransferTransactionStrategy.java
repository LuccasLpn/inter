package com.br.inter.infrastructure.strategy.transfer;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.domain.transaction.gateway.ExchangeRateGateway;
import com.br.inter.infrastructure.adapter.ExchangeRateGatewayAdapter;
import com.br.inter.infrastructure.cache.RedisCache;
import com.br.inter.infrastructure.client.ExchangeRateClient;
import com.br.inter.infrastructure.exceptions.InvalidTransactionException;
import com.br.inter.infrastructure.exceptions.ReceiverWalletNotFoundException;
import com.br.inter.infrastructure.repository.ExchangeRateRepository;
import com.br.inter.infrastructure.repository.TransactionRepository;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.repository.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class TransferTransactionStrategy extends AbstractTransactionStrategy {

    public TransferTransactionStrategy(
            TransactionRepository transactionRepository,
            WalletRepository walletRepository,
            ExchangeRateGateway exchangeRateGateway) {
        super(transactionRepository, walletRepository, exchangeRateGateway);
    }

    @Override
    protected WalletEntity findReceiverWallet(RemittanceRequest remittanceRequest, WalletEntity fromWallet) {
        WalletEntity toWallet = walletRepository.findByUuid(remittanceRequest.getToWalletUuid())
                .orElseThrow(() -> new ReceiverWalletNotFoundException("Receiver wallet not found"));
        Optional<WalletEntity> userUsdWallet = walletRepository.findWalletByUserAndCurrency(fromWallet.getUserEntity().getId());
        if (userUsdWallet.isPresent() && toWallet.getUuid().equals(userUsdWallet.get().getUuid())) {
            throw new InvalidTransactionException("You cannot transfer money to your own USD wallet.");
        }

        return toWallet;
    }

    @Override
    protected TransactionType getTransactionType() {
        return TransactionType.TRANSFER;
    }
}
