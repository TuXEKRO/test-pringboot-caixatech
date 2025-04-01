package com.round3.realestate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "employment_data")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    EmploymentContract contract;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "employment_status", columnDefinition = "varchar(255)")
    EmploymentStatus employmentStatus;

    @Column(name = "net_monthly", columnDefinition = "decimal(10,2)")
    BigDecimal netMonthly;

    @Column(columnDefinition="decimal(10,2)")
    BigDecimal salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id",
        unique = true,
        nullable = false)
    private User user;
}
