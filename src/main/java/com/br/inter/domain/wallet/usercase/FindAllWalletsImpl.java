package com.br.inter.domain.wallet.usercase;

import com.br.inter.domain.wallet.gateway.FindAllWalletsGateway;
import com.br.inter.domain.wallet.model.Wallet;

import java.util.List;
import java.util.UUID;

public class FindAllWalletsImpl implements FindAllWallets {

    private final FindAllWalletsGateway findAllWalletsGateway;

    public FindAllWalletsImpl(FindAllWalletsGateway findAllWalletsGateway) {
        this.findAllWalletsGateway = findAllWalletsGateway;
    }

    @Override
    public List<Wallet> findAllWallets(UUID userId) {
        return findAllWalletsGateway.findAllWallets(userId);
    }
}
