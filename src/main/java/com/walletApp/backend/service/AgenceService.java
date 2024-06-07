package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.Agence;
import com.walletApp.backend.repository.AgenceRepository;

@Service
public class AgenceService {
    
 @Autowired
    private AgenceRepository repository;

    public List<Agence> getAllAgences() {
        return repository.findAll();
    }

    public Optional<Agence> getAgenceById(int id) {
        return repository.findById(id);
    }

    public Agence createOrUpdateAgence(Agence agence) {
        return repository.save(agence);
    }

    public void deleteAgence(int id) {
        repository.deleteById(id);
    }

}
