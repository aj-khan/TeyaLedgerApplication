package com.teya.ledger.exception;

public class InvalidFundsException extends RuntimeException {
    public InvalidFundsException(String message) {
        super(message);
    }
}
