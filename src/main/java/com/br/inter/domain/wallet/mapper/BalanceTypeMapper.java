package com.br.inter.domain.wallet.mapper;

import com.br.inter.infrastructure.repository.enums.BalanceType;

public class BalanceTypeMapper {

    public static BalanceType toEntity(com.br.inter.domain.wallet.enums.BalanceType domainBalanceType) {
        return domainBalanceType != null ? BalanceType.valueOf(domainBalanceType.name()) : null;
    }

    public static com.br.inter.domain.wallet.enums.BalanceType toDomain(BalanceType entityBalanceType) {
        return entityBalanceType != null ? com.br.inter.domain.wallet.enums.BalanceType.valueOf(entityBalanceType.name()) : null;
    }
}
