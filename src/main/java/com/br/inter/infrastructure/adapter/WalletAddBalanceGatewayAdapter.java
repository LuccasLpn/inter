package com.br.inter.infrastructure.adapter;


import com.br.inter.domain.wallet.gateway.WalletAddBalanceGateway;
import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.infrastructure.exceptions.WalletCurrencyNotFoundException;
import com.br.inter.infrastructure.exceptions.WalletNotFoundException;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import com.br.inter.infrastructure.strategy.wallet.WalletStrategy;
import com.br.inter.infrastructure.strategy.wallet.WalletStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletAddBalanceGatewayAdapter implements WalletAddBalanceGateway {

    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public Wallet addBalance(UUID userId, BigDecimal balance, String currency) {
        WalletEntity wallet = findUserWallet(userId, currency);
        WalletStrategy strategy = WalletStrategyFactory.getStrategy(currency);
        strategy.addBalance(wallet, balance);
        walletRepository.save(wallet);
        return mapToDomain(wallet);
    }

    private WalletEntity findUserWallet(UUID userId, String currency) {
        List<WalletEntity> wallets = walletRepository.findAllByUser(userId.toString());
        if (wallets.isEmpty()) {
            throw new WalletNotFoundException("No wallet found for user with ID: " + userId);
        }
        return wallets.stream()
                .filter(wa -> wa.getBalanceType().name().equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new WalletCurrencyNotFoundException(
                        "User " + userId + " does not have a wallet for currency: " + currency));
    }

    private Wallet mapToDomain(WalletEntity walletEntity) {
        return new Wallet(walletEntity.getBalance(), walletEntity.getBalanceType().name());
    }
}
