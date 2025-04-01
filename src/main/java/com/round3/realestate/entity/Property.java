package com.round3.realestate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    PropertyAvailability availability;

    @Column(columnDefinition = "varchar(255)")
    String location;

    @Column(columnDefinition = "varchar(255)")
    String name;

    BigDecimal price;

    @Column(columnDefinition = "varchar(255)")
    String rooms;

    @Column(columnDefinition = "varchar(255)")
    String size;
}
