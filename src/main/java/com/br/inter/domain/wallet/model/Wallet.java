package com.br.inter.domain.wallet.model;

import com.br.inter.domain.user.model.User;
import com.br.inter.domain.wallet.enums.BalanceType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    private User user;
    private BigDecimal balance;
    private BalanceType balanceType;
    private UUID uuid;

    private Wallet(BalanceType balanceType) {
        this.balanceType = balanceType;
        this.balance = BigDecimal.ZERO;
    }

    public Wallet(BigDecimal balance, String balanceType) {
        this.balance = balance;
        this.balanceType = BalanceType.valueOf(balanceType);
    }

    public Wallet(BigDecimal balance, UUID uuid, String balanceType) {
        this.balance = balance;
        this.uuid = uuid;
        this.balanceType = BalanceType.valueOf(balanceType);
    }

    public static Wallet createWallet(BalanceType balanceType) {
        return new Wallet(balanceType);
    }
}

