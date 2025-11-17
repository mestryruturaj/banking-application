package io.enscryptingbytes.banking_application.exception;


import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.SOMETHING_WENT_WRONG;
import static io.enscryptingbytes.banking_application.message.ResponseMessage.VALIDATION_FAILED;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return GenericResponse.<Map<String, String>>builder()
                .message(VALIDATION_FAILED)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .response(errors)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public GenericResponse<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        return GenericResponse.<Map<String, String>>builder()
                .message(VALIDATION_FAILED)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .response(errors)
                .build();
    }

    @ExceptionHandler(BankUserException.class)
    public GenericResponse<Object> handleBankUserException(BankUserException ex) {
        return GenericResponse.<Object>builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler(BankBranchException.class)
    public GenericResponse<Object> handleBankBranchException(BankBranchException ex) {
        return GenericResponse.<Object>builder()
                .message(ex.getMessage())
                .httpStatus(ex.getHttpStatus())
                .build();
    }


    @ExceptionHandler(Exception.class)
    public GenericResponse<Object> handleException(Exception e) {
        return GenericResponse.<Object>builder()
                .message(SOMETHING_WENT_WRONG)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
