package com.br.inter.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceResponse {
    private String remittanceId;
    private String fromWalletUuid;
    private String toWalletUuid;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;
    private String status;
    private LocalDateTime timestamp;
}
