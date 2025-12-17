package io.enscryptingbytes.banking_application.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BankUserException extends Exception {
    private final HttpStatus httpStatus;

    public BankUserException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BankUserException(String message, HttpStatus httpStatus) {
        super(message);
        if (httpStatus == null) {
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            this.httpStatus = httpStatus;
        }
    }
}
