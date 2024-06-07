package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.Utilisateur;
import com.walletApp.backend.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

@Autowired
    private UtilisateurRepository repository;

    public List<Utilisateur> getAllUtilisateurs() {
        return repository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(int id) {
        return repository.findById(id);
    }

    public Utilisateur createOrUpdateUtilisateur(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        repository.deleteById(id);
    }

}
