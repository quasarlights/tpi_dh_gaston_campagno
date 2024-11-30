package com.example.cards.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transference")
public class Transference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean ingress;

    @Column(nullable = false)
    private Boolean egress;

    @Column(nullable = false)
    private Double amount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transference")
    private Card card;

}
