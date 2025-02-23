package com.br.inter.domain.transactionlimit.mapper;

import com.br.inter.infrastructure.repository.entity.TransactionLimitEntity;
import com.br.inter.infrastructure.repository.entity.UserEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionLimitMapper {

    public static TransactionLimitEntity createForUser(UserEntity user) {
        if (user == null) {
            return null;
        }
        TransactionLimitEntity transactionLimit = new TransactionLimitEntity();
        transactionLimit.setUuid(UUID.randomUUID());
        transactionLimit.setDailyLimit(getDefaultLimit(user.getDocument()));
        transactionLimit.setUsedToday(BigDecimal.ZERO);
        transactionLimit.setLastReset(LocalDate.now());
        transactionLimit.setCreatedAt(LocalDateTime.now());
        transactionLimit.setUser(user);
        return transactionLimit;
    }

    private static BigDecimal getDefaultLimit(String document) {
        return document.length() == 11 ? new BigDecimal("10000.00") : new BigDecimal("50000.00");
    }
}
