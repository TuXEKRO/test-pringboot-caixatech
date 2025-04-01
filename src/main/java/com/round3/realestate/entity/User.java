package com.round3.realestate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @Email
    @Column(unique=true, nullable = false, columnDefinition = "varchar(255)")
    String email;

    @NotBlank
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String password;

    @NotBlank
    @Column(unique=true, nullable = false, columnDefinition = "varchar(255)")
    String username;
}
