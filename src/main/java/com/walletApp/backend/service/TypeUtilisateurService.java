package com.walletApp.backend.service;

import com.walletApp.backend.model.TypeUtilisateur;
import com.walletApp.backend.repository.TypeUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeUtilisateurService {

    @Autowired
    private TypeUtilisateurRepository repository;

    public List<TypeUtilisateur> getAllTypeUtilisateurs() {
        return repository.findAll();
    }

    public Optional<TypeUtilisateur> getTypeUtilisateurById(int id) {
        return repository.findById(id);
    }

    public TypeUtilisateur createOrUpdateTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        return repository.save(typeUtilisateur);
    }

    public void deleteTypeUtilisateur(int id) {
        repository.deleteById(id);
    }
}