package com.br.inter.domain.transaction.fixture;

import com.br.inter.application.request.RemittanceRequest;
import com.br.inter.infrastructure.repository.enums.TransactionType;

import java.math.BigDecimal;

public class RemittanceRequestFixture {

    public static RemittanceRequest createValidRemittanceRequest() {
        RemittanceRequest request = new RemittanceRequest();
        request.setFromWalletUuid("11111111-1111-1111-1111-111111111111");
        request.setToWalletUuid("22222222-2222-2222-2222-222222222222");
        request.setAmount(BigDecimal.valueOf(100.00));
        request.setTransactionType(TransactionType.TRANSFER);
        return request;
    }
}
