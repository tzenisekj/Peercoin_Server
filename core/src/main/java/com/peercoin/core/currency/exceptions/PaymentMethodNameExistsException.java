package com.peercoin.core.currency.exceptions;

public class PaymentMethodNameExistsException extends Exception {
    public PaymentMethodNameExistsException(String message){
        super(message);
    }
    public PaymentMethodNameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
