package com.br.inter.domain.wallet.gateway;

import com.br.inter.domain.wallet.model.Wallet;

import java.util.List;
import java.util.UUID;

public interface FindAllWalletsGateway {

    List<Wallet> findAllWallets(UUID userId);
}
