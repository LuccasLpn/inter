package com.br.inter.application.response;

import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletAddBalanceResponse {
    private BigDecimal balance;
    private BalanceType balanceType;

    public WalletAddBalanceResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.balanceType = wallet.getBalanceType();
    }
}
