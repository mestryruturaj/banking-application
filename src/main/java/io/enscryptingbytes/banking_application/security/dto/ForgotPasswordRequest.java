package io.enscryptingbytes.banking_application.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "Email is mandatory and cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;
}
