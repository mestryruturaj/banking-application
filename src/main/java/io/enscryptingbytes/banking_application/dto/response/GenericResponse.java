package io.enscryptingbytes.banking_application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponse<T> {
    private String message;
    private HttpStatus httpStatus;
    private T response;
}
