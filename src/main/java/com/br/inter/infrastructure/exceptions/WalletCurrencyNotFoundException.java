package com.br.inter.infrastructure.exceptions;

public class WalletCurrencyNotFoundException extends RuntimeException {
    public WalletCurrencyNotFoundException(String message) {
        super(message);
    }
}
