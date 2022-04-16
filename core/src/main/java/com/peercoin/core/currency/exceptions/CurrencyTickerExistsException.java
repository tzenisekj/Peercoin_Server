package com.peercoin.core.currency.exceptions;

public class CurrencyTickerExistsException extends Exception {
    public CurrencyTickerExistsException(String message) {
        super(message);
    }

    public CurrencyTickerExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
