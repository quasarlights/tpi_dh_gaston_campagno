package com.example.cards.controller;

import com.example.cards.model.Transference;
import com.example.cards.service.TransferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movements")
public class TransferenceController {
    @Autowired
    TransferenceService transferenceService;

    @PostMapping("/create")
    public ResponseEntity<Transference> createTransference(@RequestBody Transference transference){
        transferenceService.saveTransference(transference);
        return ResponseEntity.ok(transference);
    }
}