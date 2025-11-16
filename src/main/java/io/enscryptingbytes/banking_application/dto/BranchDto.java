package io.enscryptingbytes.banking_application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class BranchDto {
    @NotBlank(message = "IFSC code is mandatory")
    @Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}", message = "IFSC must be an 11-character code starting with four letters, followed by a zero, and ending with six letters or numbers (e.g., HDFC0001980).")
    private String ifsc;
    @NotBlank(message = "Branch name is mandatory.")
    private String name;
    @NotBlank(message = "Address is mandatory.")
    private String address;
    @Min(value = 100000, message = "Pin code should be six digits")
    @Max(value = 999999, message = "Pin code should be six digits")
    private Integer pinCode;
    @NotBlank(message = "Bank name is mandatory.")
    private String bankName;
}
