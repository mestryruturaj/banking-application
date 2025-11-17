package io.enscryptingbytes.banking_application.dto;

import io.enscryptingbytes.banking_application.enums.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @NotNull(message = "User ID must be provided")
    @Positive(message = "User ID must be a positive number")
    private Long userId;
    @NotBlank(message = "IFSC code is required")
    private String ifsc;
    @NotNull(message = "Account Type must be specified")
    private AccountType accountType;
    @NotNull(message = "Balance must be provided")
    @Min(value = 0, message = "Balance cannot be negative")
    private BigDecimal balance;
}
