package com.br.inter.domain.wallet.mapper;

import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.infrastructure.repository.entity.WalletEntity;

public class WalletMapper {

    public static WalletEntity toEntity(Wallet wallet) {
        if (wallet == null) {
            return null;
        }
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setBalance(wallet.getBalance());
        walletEntity.setBalanceType(BalanceTypeMapper.toEntity(wallet.getBalanceType()));
        return walletEntity;
    }
}
