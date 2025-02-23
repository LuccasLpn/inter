package com.br.inter.infrastructure.exceptions;

public class ExchangeRateUnavailableException extends RuntimeException {
    public ExchangeRateUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateUnavailableException(String message) {
        super(message);
    }
}

