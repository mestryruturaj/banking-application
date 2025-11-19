package io.enscryptingbytes.banking_application.entity;

import io.enscryptingbytes.banking_application.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "account_seq", sequenceName = "account_sequence", allocationSize = 1)
public class Account extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    private long id;
    @Column(unique = true)
    private String accountNumber;
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

    @PrePersist
    public void generateAccountNumber() {
        if (this.accountNumber == null && this.id != 0 && this.homeBranch != null) {
            String ifsc = this.homeBranch.getIfsc();
            this.accountNumber = ifsc + String.format("%010d", this.id);
        }
    }
}
