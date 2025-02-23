package com.br.inter.domain.transaction.usercase;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;

public interface TransactionCreate {
    RemittanceResponse createTransaction(RemittanceRequest remittanceRequest);
}
