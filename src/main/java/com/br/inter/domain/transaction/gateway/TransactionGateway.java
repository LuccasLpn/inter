package com.br.inter.domain.transaction.gateway;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;

public interface TransactionGateway {
    RemittanceResponse createTransaction(RemittanceRequest remittanceRequest);
}
