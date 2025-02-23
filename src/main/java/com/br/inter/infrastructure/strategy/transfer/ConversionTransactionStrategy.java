package com.br.inter.infrastructure.strategy.transfer;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.domain.transaction.gateway.ExchangeRateGateway;
import com.br.inter.infrastructure.cache.RedisCache;
import com.br.inter.infrastructure.client.ExchangeRateClient;
import com.br.inter.infrastructure.repository.ExchangeRateRepository;
import com.br.inter.infrastructure.repository.TransactionRepository;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.repository.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class ConversionTransactionStrategy extends AbstractTransactionStrategy {


    public ConversionTransactionStrategy(
            TransactionRepository transactionRepository,
            WalletRepository walletRepository,
            ExchangeRateGateway exchangeRateGateway) {
        super(transactionRepository, walletRepository, exchangeRateGateway);
    }

    @Override
    protected WalletEntity findReceiverWallet(RemittanceRequest remittanceRequest, WalletEntity fromWallet) {
        return walletRepository.findWalletByUserAndCurrency(fromWallet.getUserEntity().getId())
                .orElseThrow(() -> new IllegalStateException("User does not have a USD wallet"));
    }

    @Override
    protected TransactionType getTransactionType() {
        return TransactionType.CONVERSION;
    }
}
