package io.enscryptingbytes.banking_application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDto {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    private String email;
    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "[1-9][0-9]{9}",
            message = "Mobile number should exactly be 10 digits.")
    private String mobileNumber;
    private String dob;
}
