package io.enscryptingbytes.banking_application.entity;

import io.enscryptingbytes.banking_application.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String accountId;
    @ManyToOne(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "ifsc",
            nullable = false)
    private Branch homeBranch;
    private AccountType accountType;
    private BigDecimal balance;
}
