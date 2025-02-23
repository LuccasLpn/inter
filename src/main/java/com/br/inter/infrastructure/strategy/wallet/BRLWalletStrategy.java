package com.br.inter.infrastructure.strategy.wallet;


import com.br.inter.infrastructure.repository.entity.WalletEntity;

import java.math.BigDecimal;

public class BRLWalletStrategy implements WalletStrategy {

    @Override
    public void addBalance(WalletEntity wallet, BigDecimal balance) {
        wallet.setBalance(wallet.getBalance().add(balance));
    }
}
