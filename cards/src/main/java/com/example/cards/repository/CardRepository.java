package com.example.cards.repository;

import com.example.cards.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository <Card, Long> {
    Card getCardById(Long id);
}
