package com.br.inter.domain.wallet.gateway;

import com.br.inter.domain.wallet.model.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletAddBalanceGateway {
    Wallet addBalance(UUID userId, BigDecimal balance, String currency);
}
