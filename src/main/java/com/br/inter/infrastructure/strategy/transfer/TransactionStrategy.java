package com.br.inter.infrastructure.strategy.transfer;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.application.response.RemittanceResponse;

public interface TransactionStrategy {
    RemittanceResponse processTransaction(RemittanceRequest remittanceRequest);
}
