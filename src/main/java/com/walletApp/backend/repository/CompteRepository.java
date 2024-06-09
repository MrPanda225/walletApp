package com.walletApp.backend.repository;

import com.walletApp.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletApp.backend.model.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    Compte findByUser(Utilisateur user);
    Compte findByAgence(Agence agence);
}