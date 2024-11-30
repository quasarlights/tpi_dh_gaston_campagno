package com.example.cards.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroTarjeta; // Número de tarjeta único.

    @Column(nullable = false)
    private Long accountId; // ID del usuario asociado.

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Propaga cambios y elimina Account al eliminar User
    @JoinColumn(name = "transference_id", referencedColumnName = "id") // Usa account_id como FK
    @JsonIgnore
    private Transference transference;
}
