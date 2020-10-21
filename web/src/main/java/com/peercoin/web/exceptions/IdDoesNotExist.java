package com.peercoin.web.exceptions;

public class IdDoesNotExist extends Exception {
    public IdDoesNotExist(String message) {
        super(message);
    }

    public IdDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }
}
