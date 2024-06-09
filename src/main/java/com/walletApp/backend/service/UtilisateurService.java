package com.walletApp.backend.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.walletApp.backend.config.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.*;
import com.walletApp.backend.repository.*;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository repository;

    @Autowired
    private CompteRepository compteRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return repository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(int id) {
        return repository.findById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    public Utilisateur createOrUpdateUtilisateur(Utilisateur utilisateur) {
        return repository.save(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        repository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }

    public boolean phoneNumberExists(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber) != null;
    }

    public Compte createCompte(Utilisateur utilisateur, Agence agence, Fournisseur fournisseur, TypeCpt typeCpt) {
        Compte compte = new Compte();
        compte.setNum_cpt(AccountNumberGenerator.generateAccountNumber());
        compte.setDate_creation(new Date(System.currentTimeMillis()));
        compte.setSolde(0.0);
        compte.setUser(utilisateur);
        compte.setAgence(agence);
        compte.setFournisseur(fournisseur);
        compte.setType_cpt(typeCpt);
        return compteRepository.save(compte);
    }

    public Utilisateur login(String email, String password){
        return repository.findByEmailAndPassword(email, password);
    }

    public Utilisateur findUserWithAccounts(int userId) {
        Optional<Utilisateur> user = repository.findById(userId);
        return user.orElse(null);
    }

}
