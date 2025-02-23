package com.br.inter.domain.wallet.usercase;

import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.domain.wallet.gateway.WalletAddBalanceGateway;
import com.br.inter.domain.wallet.model.Wallet;

import java.util.UUID;

public class CreateBalanceWalletImpl implements CreateBalanceWallet {

    private final WalletAddBalanceGateway walletAddBalanceGateway;

    public CreateBalanceWalletImpl(WalletAddBalanceGateway walletAddBalanceGateway) {
        this.walletAddBalanceGateway = walletAddBalanceGateway;
    }

    @Override
    public Wallet execute(CreateBalanceRequest request, UUID userId) {
        return walletAddBalanceGateway.addBalance(
                userId,
                request.getBalance(),
                request.getCurrency()
        );
    }
}
