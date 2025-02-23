package com.br.inter.infrastructure.repository;

import com.br.inter.infrastructure.repository.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {

    @Query("SELECT e FROM ExchangeRateEntity e ORDER BY e.createdAt DESC LIMIT 1")
    Optional<ExchangeRateEntity> findLatestExchangeRate();
}
