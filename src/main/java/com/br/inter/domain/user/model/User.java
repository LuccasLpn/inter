package com.br.inter.domain.user.model;

import com.br.inter.domain.wallet.enums.BalanceType;
import com.br.inter.domain.wallet.model.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String document;
    @JsonIgnore
    private List<Wallet> wallets;

    public User(String name, String email, String password, String document) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.document = document;
        this.wallets = new ArrayList<>();
        this.wallets.add(Wallet.createWallet(BalanceType.BRL));
        this.wallets.add(Wallet.createWallet(BalanceType.USD));
    }

    public User(String name, String email, String document) {
        this.name = name;
        this.email = email;
        this.document = document;
    }

    public static User createNewUser(String name, String email, String password, String document) {
        return new User(name, email, password, document);
    }
}
