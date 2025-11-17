package io.enscryptingbytes.banking_application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class Branch extends AuditableEntity {
    @Id
    private String ifsc;
    private String name;
    private String address;
    private Integer pinCode;
    private String bankName;
}
