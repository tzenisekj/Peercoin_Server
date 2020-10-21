package com.peercoin.core.currency.exceptions;

public class CurrencyDoesNotExistException extends Exception {
    public CurrencyDoesNotExistException(String message) {
        super(message);
    }

    public CurrencyDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
