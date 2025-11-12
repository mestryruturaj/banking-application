package io.enscryptingbytes.banking_application.repository;

import io.enscryptingbytes.banking_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}



