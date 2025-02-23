package com.br.inter.infrastructure.exceptions;

public class ReceiverWalletNotFoundException extends RuntimeException {
    public ReceiverWalletNotFoundException(String message) {
        super(message);
    }
}
