package com.br.inter.domain.transaction.usercase;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;
import com.br.inter.domain.transaction.gateway.TransactionGateway;

public class TransactionCreateImpl implements TransactionCreate {

    private final TransactionGateway transactionGateway;

    public TransactionCreateImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public RemittanceResponse createTransaction(RemittanceRequest remittanceRequest) {
        return transactionGateway.createTransaction(remittanceRequest);
    }
}
