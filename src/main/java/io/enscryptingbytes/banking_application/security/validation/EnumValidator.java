package io.enscryptingbytes.banking_application.security.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> allowedValues;
    private String validValuesMessage;
    private ValidEnum constraintAnnotation;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 1. Handle null/empty cases (let @NotBlank handle if necessary)
        if (StringUtils.isBlank(value)) {
            return true; // We assume @NotBlank handles the mandatory check
        }

        // 2. Check if the provided value is in the dynamically created set
        boolean isValid = allowedValues.contains(value);

        // 3. Customize the error message to list the valid values
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    constraintAnnotation.message() + " Allowed values are: [" + this.validValuesMessage + "]"
            ).addConstraintViolation();
        }

        return isValid;
    }

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
        this.allowedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());

        this.validValuesMessage = allowedValues.stream().collect(Collectors.joining(", "));
    }
}
