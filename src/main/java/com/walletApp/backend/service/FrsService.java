package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.Fournisseur;
import com.walletApp.backend.repository.FrsRepository;


@Service
public class FrsService {
    
 @Autowired
    private FrsRepository repository;

    public List<Fournisseur> getAllFrs() {
        return repository.findAll();
    }

    public Optional<Fournisseur> getFournisseurById(int id) {
        return repository.findById(id);
    }

    public Fournisseur createOrUpdateFournisseur(Fournisseur frs) {
        return repository.save(frs);
    }

    public void deleteFournisseur(int id) {
        repository.deleteById(id);
    }


}
