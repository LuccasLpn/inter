package com.br.inter.infrastructure.fixture;

import com.br.inter.domain.user.model.User;
import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class UserFixture {

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Fulano de Tal");
        user.setEmail("fulano@example.com");
        user.setPassword("senha123");
        user.setDocument("12345678900");
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(Wallet.createWallet(BalanceType.USD));
        wallets.add(Wallet.createWallet(BalanceType.BRL));
        user.setWallets(wallets);
        return user;
    }
}
