package com.example.registry_login_logout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
//@JsonIgnoreProperties({"users"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cvu;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private Double saldo = 0.0; // Saldo inicializado en 0.0

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account") // Aquí el 'mappedBy' indica que este es el lado no propietario
    //@JsonIgnore
    private Users users;

}