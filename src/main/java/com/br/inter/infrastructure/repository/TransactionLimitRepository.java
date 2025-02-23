package com.br.inter.infrastructure.repository;

import com.br.inter.infrastructure.repository.entity.TransactionLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimitEntity, Long> {

    @Modifying
    @Query("UPDATE TransactionLimitEntity t SET t.usedToday = 0.00, t.lastReset = CURRENT_DATE")
    void resetDailyLimits();
}
