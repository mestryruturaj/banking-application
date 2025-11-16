package io.enscryptingbytes.banking_application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Branch {
    @Id
    private String ifsc;
    private String name;
    private String address;
    private Integer pinCode;
    private String bankName;
}
