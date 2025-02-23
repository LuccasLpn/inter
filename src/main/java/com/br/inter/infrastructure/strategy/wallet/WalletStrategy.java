package com.br.inter.infrastructure.strategy.wallet;

import com.br.inter.infrastructure.repository.entity.WalletEntity;

import java.math.BigDecimal;

public interface WalletStrategy {
    void addBalance(WalletEntity wallet, BigDecimal balance);
}
