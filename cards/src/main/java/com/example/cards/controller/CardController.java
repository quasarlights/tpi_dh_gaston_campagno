package com.example.cards.controller;

import com.example.cards.model.Card;
import com.example.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    CardService cardService;



    @PostMapping("/create/{id}")
    public Card createCard(@PathVariable Long id,
                           @RequestBody Card card){

        return cardService.createCard(id, card);
    }
}