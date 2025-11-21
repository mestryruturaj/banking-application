package io.enscryptingbytes.banking_application.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class BankAuthException extends Exception {
    private HttpStatus httpStatus;

    public BankAuthException(String message) {
        this(message, HttpStatus.UNAUTHORIZED);
    }

    public BankAuthException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = Objects.requireNonNullElse(httpStatus, HttpStatus.UNAUTHORIZED);
    }
}
