package com.br.inter.domain.wallet.usercase;

import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.domain.wallet.model.Wallet;
import java.util.UUID;

public interface CreateBalanceWallet {

    Wallet execute(CreateBalanceRequest request, UUID userId);
}
