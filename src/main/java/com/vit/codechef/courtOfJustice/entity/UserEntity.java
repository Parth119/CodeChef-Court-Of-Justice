package com.vit.codechef.courtOfJustice.entity;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password; // Bcrypt encoded

    @Enumerated(EnumType.STRING)
    private ServiceConstants.Role role;
}