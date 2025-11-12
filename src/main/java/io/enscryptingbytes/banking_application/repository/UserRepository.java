package io.enscryptingbytes.banking_application.repository;

import io.enscryptingbytes.banking_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByMobileNumber(String mobileNumber);
}



