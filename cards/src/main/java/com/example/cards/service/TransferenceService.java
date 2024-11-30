package com.example.cards.service;

import com.example.cards.model.Transference;
import com.example.cards.repository.TransferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenceService {
    @Autowired
    TransferenceRepository transferenceRepository;

    public Transference saveTransference (Transference transference){
        return transferenceRepository.save(transference);
    }

}
