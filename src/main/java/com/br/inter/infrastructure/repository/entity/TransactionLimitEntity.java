package com.br.inter.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_limits")
public class TransactionLimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "daily_limit", nullable = false, precision = 15, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "used_today", nullable = false, precision = 15, scale = 2)
    private BigDecimal usedToday = BigDecimal.ZERO;

    @Column(name = "last_reset", nullable = false)
    private LocalDate lastReset;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}

