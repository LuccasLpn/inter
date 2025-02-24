package com.br.inter.domain.transaction.fixture;

import com.br.inter.application.response.RemittanceResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RemittanceResponseFixture {


    public static RemittanceResponse createValidRemittanceResponse() {
        return new RemittanceResponse(
                "12345678-1234-1234-1234-123456789012",
                "11111111-1111-1111-1111-111111111111",
                "22222222-2222-2222-2222-222222222222",
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(95.00),
                BigDecimal.valueOf(0.95),
                "SUCCESS",
                LocalDateTime.now()
        );
    }
}
