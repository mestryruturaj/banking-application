package io.enscryptingbytes.banking_application.repository;


import io.enscryptingbytes.banking_application.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, String> {
    public Optional<List<Branch>> findByPinCode(int pinCode);

    public Optional<Branch> findByName(String name);
}
