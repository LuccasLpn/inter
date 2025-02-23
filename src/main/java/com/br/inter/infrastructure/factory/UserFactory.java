package com.br.inter.infrastructure.factory;

import com.br.inter.domain.user.model.User;
import com.br.inter.domain.transactionlimit.mapper.TransactionLimitMapper;
import com.br.inter.domain.user.mapper.UserMapper;
import com.br.inter.domain.wallet.mapper.WalletMapper;
import com.br.inter.infrastructure.repository.entity.TransactionLimitEntity;
import com.br.inter.infrastructure.repository.entity.UserEntity;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.security.PasswordEncryptor;

import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {

    public static UserEntity createUserEntity(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        userEntity.setPassword(PasswordEncryptor.encryptPassword(user.getPassword()));
        List<WalletEntity> wallets = user.getWallets().stream()
                .map(wallet -> {
                    WalletEntity walletEntity = WalletMapper.toEntity(wallet);
                    walletEntity.setUserEntity(userEntity);
                    return walletEntity;
                })
                .collect(Collectors.toList());
        userEntity.setWallets(wallets);
        TransactionLimitEntity limit = TransactionLimitMapper.createForUser(userEntity);
        userEntity.setTransactionLimitEntity(limit);
        return userEntity;
    }
}
