package io.enscryptingbytes.banking_application.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRequest {
    @NotBlank(message = "Email is mandatory and cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;
    @NotBlank(message = "Password is a mandatory field")
    @Length(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
