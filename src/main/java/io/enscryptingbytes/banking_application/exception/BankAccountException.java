package io.enscryptingbytes.banking_application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BankAccountException extends Exception {
    private final HttpStatus httpStatus;

    public BankAccountException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public BankAccountException(String message, HttpStatus httpStatus) {
        super(message);
        if (httpStatus != null) {
            this.httpStatus = httpStatus;
        } else {
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
