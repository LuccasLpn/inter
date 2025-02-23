package com.br.inter.infrastructure.adapter;


import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.gateway.TransactionGateway;
import com.br.inter.infrastructure.strategy.transfer.TransactionStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TransactionGatewayAdapter implements TransactionGateway {

    private final TransactionStrategyFactory transactionStrategyFactory;

    @Override
    public RemittanceResponse createTransaction(RemittanceRequest remittanceRequest) {
        return transactionStrategyFactory.getStrategy(remittanceRequest.getTransactionType())
                .processTransaction(remittanceRequest);
    }
}
