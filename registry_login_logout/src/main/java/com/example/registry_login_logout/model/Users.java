package com.example.registry_login_logout.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {
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


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Propaga cambios y elimina Account al eliminar User
    @JoinColumn(name = "account_id", referencedColumnName = "id") // Usa account_id como FK
    private Account account;
}
