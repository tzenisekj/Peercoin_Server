package com.peercoin.core.currency.exceptions;

public class PaymentMethodNameDoesNotExistException extends Exception {
    public PaymentMethodNameDoesNotExistException(String message) {
        super(message);
    }

    public PaymentMethodNameDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
