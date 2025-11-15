package io.enscryptingbytes.banking_application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Email is mandatory and cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;
    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "[1-9][0-9]{9}",
            message = "Mobile number should exactly be 10 digits.")
    private String mobileNumber;
    private String dob;
}
