package com.peercoin.core.currency.exceptions;

public class InvalidStateException extends Exception {
    public InvalidStateException(String message) {
        super(message);
    }
    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
