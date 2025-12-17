package io.enscryptingbytes.banking_application.security.dto;

import io.enscryptingbytes.banking_application.security.type.ChangePasswordType;
import io.enscryptingbytes.banking_application.security.validation.ValidEnum;
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
public class ChangePasswordRequest {
    @NotBlank(message = "Email is mandatory and cannot be empty")
    @Email(message = "Email format is invalid")
    private String email;
    @ValidEnum(enumClass = ChangePasswordType.class)
    private ChangePasswordType changePasswordType;
    private String oldPassword;
    private String resetPasswordToken;
    @NotBlank(message = "New Password is a mandatory field")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$", message = "Password must consist at least one Uppercase, Lowercase, Digit and Special character. Password must be 8 characters long.")
    private String newPassword;
    @NotBlank(message = "Confirm New Password is a mandatory field")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}$", message = "Password must consist at least one Uppercase, Lowercase, Digit and Special character. Password must be 8 characters long.")
    private String confirmNewPassword;

}
