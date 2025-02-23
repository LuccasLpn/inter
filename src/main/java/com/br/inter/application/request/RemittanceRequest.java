package com.br.inter.application.request;

import com.br.inter.infrastructure.repository.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RemittanceRequest {

    @NotNull(message = "fromWalletUuid is required")
    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "Invalid UUID format")
    private String fromWalletUuid;

    @NotNull(message = "toWalletUuid is required")
    @Pattern(regexp = "^[a-f0-9\\-]{36}$", message = "Invalid UUID format")
    private String toWalletUuid;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "TransactionType is required")
    private TransactionType transactionType;
}
