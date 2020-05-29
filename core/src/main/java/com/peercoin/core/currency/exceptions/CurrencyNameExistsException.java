package com.peercoin.core.currency.exceptions;

public class CurrencyNameExistsException extends Exception {
    public CurrencyNameExistsException(String message) {
        super(message);
    }

    public CurrencyNameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
