package com.br.inter.infrastructure.repository;

import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.infrastructure.repository.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {


    @Query(
            value = """
                   SELECT wa.*
                   FROM wallets wa
                   JOIN users uss ON uss.id = wa.user_id
                   WHERE uss.uuid = UNHEX(REPLACE(:uuid, '-', ''))
                   """, nativeQuery = true
    )
    List<WalletEntity> findAllByUser(@Param("uuid") String uuid);



    @Query(
            value = """
                   SELECT wa.*
                   FROM wallets wa
                   WHERE wa.uuid = UNHEX(REPLACE(:uuid, '-', ''))
                   and wa.balance_type = 'USD'
                   """, nativeQuery = true
    )
    Optional<WalletEntity> findByUuidAndUsd(@Param("uuid") String uuid);

    @Query(
            value = """
                   SELECT wa.*
                   FROM wallets wa
                   WHERE wa.uuid = UNHEX(REPLACE(:uuid, '-', ''))
                   """, nativeQuery = true
    )
    Optional<WalletEntity> findByUuid(@Param("uuid") String uuid);


    @Query("SELECT w FROM WalletEntity w WHERE w.userEntity.id = :userId AND w.balanceType = 'USD'")
    Optional<WalletEntity> findWalletByUserAndCurrency(Long userId);

}
