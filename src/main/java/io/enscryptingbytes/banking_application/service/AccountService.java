package io.enscryptingbytes.banking_application.service;

import io.enscryptingbytes.banking_application.dto.AccountDto;
import io.enscryptingbytes.banking_application.entity.Account;
import io.enscryptingbytes.banking_application.entity.Branch;
import io.enscryptingbytes.banking_application.entity.User;
import io.enscryptingbytes.banking_application.exception.BankAccountException;
import io.enscryptingbytes.banking_application.exception.BankBranchException;
import io.enscryptingbytes.banking_application.exception.BankUserException;
import io.enscryptingbytes.banking_application.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static io.enscryptingbytes.banking_application.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final UserService userService;
    private final BranchService branchService;
    private final AccountRepository accountRepository;

    @Transactional
    public AccountDto createAccount(AccountDto accountDto) throws BankAccountException {
        try {
            Optional<User> existingUserOptional = userService.findUserById(accountDto.getUserId());
            if (existingUserOptional.isEmpty()) {
                throw new BankAccountException(USER_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
            }

            User user = existingUserOptional.get();
            Branch branch = branchService.findBranchByIfsc(accountDto.getIfsc());
            Account newAccount = Account.builder()
                    .accountType(accountDto.getAccountType())
                    .balance(accountDto.getBalance())
                    .user(user)
                    .homeBranch(branch)
                    .build();

            newAccount = accountRepository.save(newAccount);
            return convertAccountToAccountDto(newAccount);
        } catch (BankUserException ex) {
            log.error(ex.toString());
            throw new BankAccountException(ACCOUNT_CREATION_FAILED_DUE_TO_INVALID_USER, HttpStatus.BAD_REQUEST);
        } catch (BankBranchException ex) {
            log.error(ex.toString());
            throw new BankAccountException(ACCOUNT_CREATION_FAILED_DUE_TO_INVALID_BRANCH, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public AccountDto getAccount(String accountNumber) throws BankAccountException {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BankAccountException(ACCOUNT_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        return convertAccountToAccountDto(account);
    }

    @Transactional
    public AccountDto updateAccount(String accountNumber, AccountDto accountDto) throws BankAccountException {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BankAccountException(ACCOUNT_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        if (accountDto.getAccountType() != null) {
            account.setAccountType(accountDto.getAccountType());
        }
        return convertAccountToAccountDto(account);
    }

    @Transactional
    public AccountDto deleteAccount(String accountNumber) throws BankAccountException {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new BankAccountException(ACCOUNT_DOES_NOT_EXISTS, HttpStatus.NOT_FOUND));
        accountRepository.delete(existingAccount);
        return convertAccountToAccountDto(existingAccount);
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
