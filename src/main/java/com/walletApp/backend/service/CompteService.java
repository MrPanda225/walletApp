package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.Compte;
import com.walletApp.backend.repository.CompteRepository;


@Service
public class CompteService {
 
    @Autowired
    private CompteRepository repository;

    public List<Compte> getAllComptes() {
        return repository.findAll();
    }

    public Optional<Compte> getCompteById(String id) {
        return repository.findById(id);
    }

    public Compte createOrUpdateCompte(Compte compte) {
        return repository.save(compte);
    }

    public void deleteCompte(String id) {
        repository.deleteById(id);
    }


}
