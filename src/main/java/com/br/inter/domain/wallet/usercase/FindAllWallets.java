package com.br.inter.domain.wallet.usercase;

import com.br.inter.domain.wallet.model.Wallet;

import java.util.List;
import java.util.UUID;

public interface FindAllWallets {

    List<Wallet> findAllWallets(UUID userId);
}
