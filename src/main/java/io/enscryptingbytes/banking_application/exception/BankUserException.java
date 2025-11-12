package io.enscryptingbytes.banking_application.exception;

public class BankUserException extends RuntimeException {
    public BankUserException(String message) {
        super(message);
    }
}
