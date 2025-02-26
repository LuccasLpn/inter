package com.br.inter.infrastructure.adapter;

import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.gateway.FindAllWalletsGateway;
import com.br.inter.domain.wallet.model.Wallet;
import com.br.inter.infrastructure.repository.WalletRepository;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllWalletsGatewayAdapter implements FindAllWalletsGateway {

    private final WalletRepository walletRepository;

    @Override
    public List<Wallet> findAllWallets(UUID userId) {
        List<WalletEntity> lsWallets = walletRepository.findAllByUser(userId.toString());
        return lsWallets.stream()
                .map(this::convertToWallet)
                .collect(Collectors.toList());
    }

    private Wallet convertToWallet(WalletEntity entity) {
        return new Wallet(
                entity.getBalance(),
                entity.getUuid(),
                entity.getBalanceType().name()
        );
    }

}
