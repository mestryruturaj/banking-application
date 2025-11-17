package io.enscryptingbytes.banking_application.service;

import io.enscryptingbytes.banking_application.dto.AccountDto;
import io.enscryptingbytes.banking_application.dto.BranchDto;
import io.enscryptingbytes.banking_application.dto.UserDto;
import io.enscryptingbytes.banking_application.entity.Account;
import io.enscryptingbytes.banking_application.exception.BankBranchException;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserService userService;
    private final BranchService branchService;

    public AccountDto createAccountDto(AccountDto accountDto) {
        try {
            UserDto userDto = userService.getUser(accountDto.getUserId());
            BranchDto branchDto = branchService.getBranch(accountDto.getIfsc());
        } catch (BankUserException | BankBranchException ex) {
            return null;
        }
    }

    public static AccountDto convertAccountToAccountDto(Account account) {
        return AccountDto.builder()
                .accountType(account.getAccountType())
                .ifsc(account.getHomeBranch().getIfsc())
                .userId(account.getUser().getId())
                .balance(account.getBalance())
                .build();
    }
}
