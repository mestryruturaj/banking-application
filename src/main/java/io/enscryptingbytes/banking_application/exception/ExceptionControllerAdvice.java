package io.enscryptingbytes.banking_application.exception;


import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.SOMETHING_WENT_WRONG;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public GenericResponse<Object> handleException(Exception e) {
        return GenericResponse.<Object>builder()
                .message(SOMETHING_WENT_WRONG)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
