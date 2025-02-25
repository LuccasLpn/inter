package com.br.inter.infrastructure.adapter;

import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.infrastructure.exceptions.WalletCurrencyNotFoundException;
import com.br.inter.infrastructure.exceptions.WalletNotFoundException;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.repository.enums.BalanceType;
import com.br.inter.infrastructure.strategy.wallet.WalletStrategy;
import com.br.inter.infrastructure.strategy.wallet.WalletStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class WalletAddBalanceGatewayAdapterTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletStrategy walletStrategy;

    @InjectMocks
    private WalletAddBalanceGatewayAdapter walletAddBalanceGatewayAdapter;

    private UUID userId;
    private String currency;
    private WalletEntity walletEntity;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        currency = "USD";
        walletEntity = new WalletEntity();
        walletEntity.setBalance(BigDecimal.ZERO);
        walletEntity.setBalanceType(BalanceType.valueOf(currency));
        WalletStrategyFactory.getStrategy(currency);
    }

    @Test
    void shouldAddBalanceSuccessfully() {
        BigDecimal balanceToAdd = new BigDecimal("100.00");
        BDDMockito.when(walletRepository.findAllByUser(userId.toString()))
                .thenReturn(List.of(walletEntity));
        BDDMockito.when(walletRepository.save(walletEntity)).thenReturn(walletEntity);
        Wallet result = walletAddBalanceGatewayAdapter.addBalance(userId, balanceToAdd, currency);
        assertEquals(balanceToAdd, result.getBalance());
        assertEquals(currency, result.getBalanceType().name());
    }

    @Test
    void shouldThrowWalletNotFoundExceptionWhenNoWalletExists() {
        BDDMockito.when(walletRepository.findAllByUser(userId.toString())).thenReturn(List.of());
        assertThrows(WalletNotFoundException.class,
                () -> walletAddBalanceGatewayAdapter.addBalance(userId, new BigDecimal("100.00"), currency));
    }

    @Test
    void shouldThrowWalletCurrencyNotFoundExceptionWhenWalletWithCurrencyDoesNotExist() {
        WalletEntity differentCurrencyWallet = new WalletEntity();
        differentCurrencyWallet.setBalance(BigDecimal.ZERO);
        differentCurrencyWallet.setBalanceType(BalanceType.USD);
        BDDMockito.when(walletRepository.findAllByUser(userId.toString()))
                .thenReturn(List.of(differentCurrencyWallet));
        assertThrows(WalletCurrencyNotFoundException.class,
                () -> walletAddBalanceGatewayAdapter.addBalance(userId, new BigDecimal("100.00"), "EUR"));
    }

}