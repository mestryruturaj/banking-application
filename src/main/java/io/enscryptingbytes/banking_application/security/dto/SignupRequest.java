package io.enscryptingbytes.banking_application.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignupRequest {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Email is mandatory and cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;
    @NotBlank(message = "Password is a mandatory field")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$", message = "Password must consist at least one Uppercase, Lowercase, Digit and Special character. Password must be 8 characters long.")
    private String password;
    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "[1-9][0-9]{9}",
            message = "Mobile number should exactly be 10 digits.")
    private String mobileNumber;
    private String dob;
}
