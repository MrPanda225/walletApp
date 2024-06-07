package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.TypeCpt;
import com.walletApp.backend.repository.TypeCompteRepository;

@Service
public class TypeCompteService {
    
    @Autowired
    private TypeCompteRepository repository;

    public List<TypeCpt> getAllTypeComptes() {
        return repository.findAll();
    }

    public Optional<TypeCpt> getTypeCptById(int id) {
        return repository.findById(id);
    }

    public TypeCpt createOrUpdateTypeUtilisateur(TypeCpt typeCompte) {
        return repository.save(typeCompte);
    }

    public void deleteTypeCompte(int id) {
        repository.deleteById(id);
    }

}
