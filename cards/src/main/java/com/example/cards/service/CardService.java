package com.example.cards.service;

import com.example.cards.exception.AccountNotFoundException;
import com.example.cards.feign.AccountClient;
import com.example.cards.model.Card;
import com.example.cards.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Long id ,Card card) {
        // Validar que el usuario exista en el microservicio de usuarios.
        boolean accountExists = accountClient.getAccountById(id);

        if (!accountExists) {
            throw new AccountNotFoundException("Account does not exist");
        }
        card.setAccountId(id);
        // Guardar la tarjeta si el usuario es válido.
        return cardRepository.save(card);
    }

}
