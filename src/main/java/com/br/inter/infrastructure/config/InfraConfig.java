package com.br.inter.infrastructure.config;

import com.br.inter.domain.transaction.gateway.TransactionGateway;
import com.br.inter.domain.transaction.usercase.TransactionCreate;
import com.br.inter.domain.transaction.usercase.TransactionCreateImpl;
import com.br.inter.domain.user.gateway.CreateUserGateway;
import com.br.inter.domain.user.usercase.CreateUser;
import com.br.inter.domain.user.usercase.CreateUserImpl;
import com.br.inter.domain.wallet.gateway.WalletAddBalanceGateway;
import com.br.inter.domain.wallet.usercase.CreateBalanceWallet;
import com.br.inter.domain.wallet.usercase.CreateBalanceWalletImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Bean
    public CreateUser createUser(CreateUserGateway createUserGateway) {
        return new CreateUserImpl(createUserGateway);
    }

    @Bean
    public CreateBalanceWallet createBalanceWallet(WalletAddBalanceGateway walletAddBalanceGateway) {
        return new CreateBalanceWalletImpl(walletAddBalanceGateway);
    }

    @Bean
    public TransactionCreate createTransaction(TransactionGateway transactionGateway) {
        return new TransactionCreateImpl(transactionGateway);
    }
}
