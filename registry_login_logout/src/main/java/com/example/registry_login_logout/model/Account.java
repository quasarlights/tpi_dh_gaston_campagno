package com.example.registry_login_logout.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cvu;

    @Column(nullable = false)
    private String alias;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account") // Aquí el 'mappedBy' indica que este es el lado no propietario
    private Users users;

}