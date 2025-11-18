package io.enscryptingbytes.banking_application.controller;

import io.enscryptingbytes.banking_application.dto.AccountDto;
import io.enscryptingbytes.banking_application.dto.response.GenericResponse;
import io.enscryptingbytes.banking_application.exception.BankAccountException;
import io.enscryptingbytes.banking_application.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static io.enscryptingbytes.banking_application.message.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account", produces = "application/json")
@Validated
public class AccountController {
    private final AccountService accountService;

    @PostMapping(value = "", consumes = "application/json")
    public GenericResponse<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) throws BankAccountException {
        return GenericResponse.<AccountDto>builder()
                .message(ACCOUNT_CREATION_PASSED)
                .httpStatus(HttpStatus.CREATED)
                .response(accountService.createAccount(accountDto))
                .build();
    }

    @GetMapping(value = "/{accountNumber}")
    public GenericResponse<AccountDto> getAccount(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}[0-9]{10}", message = "Invalid account number.") @PathVariable String accountNumber) throws BankAccountException {
        return GenericResponse.<AccountDto>builder()
                .message(ACCOUNT_FOUND)
                .httpStatus(HttpStatus.OK)
                .response(accountService.getAccount(accountNumber))
                .build();
    }

    @PatchMapping(value = "/{accountNumber}")
    public GenericResponse<AccountDto> updateAccount(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}[0-9]{10}", message = "Invalid account number.") @PathVariable String accountNumber, @Valid @RequestBody AccountDto accountDto) throws BankAccountException {
        return GenericResponse.<AccountDto>builder()
                .message(ACCOUNT_UPDATED)
                .httpStatus(HttpStatus.OK)
                .response(accountService.updateAccount(accountNumber, accountDto))
                .build();
    }

    @DeleteMapping(value = "/{accountNumber}")
    public GenericResponse<AccountDto> deleteAccount(@Pattern(regexp = "[A-Z]{4}0[0-9A-Z]{6}[0-9]{10}", message = "Invalid account number.") @PathVariable String accountNumber) throws BankAccountException {
        return GenericResponse.<AccountDto>builder()
                .message(ACCOUNT_DELETED)
                .httpStatus(HttpStatus.OK)
                .response(accountService.deleteAccount(accountNumber))
                .build();
    }

}
