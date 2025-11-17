package io.enscryptingbytes.banking_application.repository;

import io.enscryptingbytes.banking_application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByAccountNumber(String accountNumber);

    public Optional<List<Account>> findByUserId(Long userId);
}
