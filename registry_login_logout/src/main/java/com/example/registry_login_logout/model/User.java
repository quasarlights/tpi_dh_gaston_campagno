package com.example.registry_login_logout.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nyap;
    @Column(nullable = false)
    private String dni;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String cvu;
    @Column(nullable = false)
    private String alias;
}
