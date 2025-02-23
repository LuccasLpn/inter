package com.br.inter.infrastructure.strategy.transfer;

import com.br.inter.infrastructure.repository.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class TransactionStrategyFactory {

    private final Map<TransactionType, TransactionStrategy> strategies = new EnumMap<>(TransactionType.class);

    public TransactionStrategyFactory(
            TransferTransactionStrategy transferTransactionStrategy,
            ConversionTransactionStrategy conversionTransactionStrategy) {
        strategies.put(TransactionType.TRANSFER, transferTransactionStrategy);
        strategies.put(TransactionType.CONVERSION, conversionTransactionStrategy);
    }

    public TransactionStrategy getStrategy(TransactionType transactionType) {
        TransactionStrategy strategy = strategies.get(transactionType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for transaction type: " + transactionType);
        }
        return strategy;
    }
}
