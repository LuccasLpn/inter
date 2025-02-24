package com.br.inter.domain.wallet.fixture;

import com.br.inter.domain.user.fixture.UserFixture;
import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.model.Wallet;

import java.math.BigDecimal;

public class WalletFixture {

    public static Wallet createMockWallet(BalanceType balanceType) {
        Wallet wallet = Wallet.createWallet(balanceType);
        wallet.setUser(UserFixture.createMockUser());
        wallet.setBalance(BigDecimal.valueOf(100.00));
        return wallet;
    }
}
