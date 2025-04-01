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
@Table(name = "mortgages")
public class Mortgage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "monthly_payment", columnDefinition = "decimal(10,2)")
    BigDecimal monthlyPayment;

    @Column(name = "number_of_months", nullable = false)
    Integer numberOfMonths;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "property_id", referencedColumnName = "id", nullable = false)
    private Property property;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
