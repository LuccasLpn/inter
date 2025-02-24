package com.br.inter.domain.wallet.usercase;

import com.br.inter.application.request.CreateBalanceRequest;
import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.fixture.WalletFixture;
import com.br.inter.domain.wallet.gateway.WalletAddBalanceGateway;
import com.br.inter.domain.wallet.model.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class CreateBalanceWalletImplTest {

    @InjectMocks
    private CreateBalanceWalletImpl createBalanceWallet;

    @Mock
    private WalletAddBalanceGateway walletAddBalanceGateway;


    @Test
    void testExecuteAddsBalanceSuccessfully() {
        BigDecimal amountToAdd = BigDecimal.valueOf(50);
        Wallet wallet = WalletFixture.createMockWallet(BalanceType.USD);
        wallet.setBalance(BigDecimal.ZERO);
        Wallet updatedWallet = WalletFixture.createMockWallet(BalanceType.USD);
        updatedWallet.setBalance(amountToAdd);
        BDDMockito.when(walletAddBalanceGateway.addBalance(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(updatedWallet);
        CreateBalanceRequest request = new CreateBalanceRequest(BigDecimal.valueOf(100), "USD");
        Wallet result = createBalanceWallet.execute(request, UUID.randomUUID());
        assertNotNull(result, "The wallet should not be null.");
        assertEquals(amountToAdd, result.getBalance(), "The wallet balance should be updated with the added amount.");
    }
}
