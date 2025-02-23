package com.br.inter.infrastructure.exceptions;

public class SenderWalletNotFoundException extends RuntimeException {
    public SenderWalletNotFoundException(String message) {
        super(message);
    }
}
