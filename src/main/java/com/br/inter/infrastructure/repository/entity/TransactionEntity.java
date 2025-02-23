package com.br.inter.infrastructure.repository.entity;

import com.br.inter.infrastructure.repository.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id", nullable = false)
    private WalletEntity fromWalletEntity;

    @ManyToOne
    @JoinColumn(name = "to_wallet_id", nullable = false)
    private WalletEntity toWalletEntity;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_rate_id")
    private ExchangeRateEntity exchangeRateEntity;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
