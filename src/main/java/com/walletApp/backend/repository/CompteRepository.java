package com.walletApp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletApp.backend.model.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    
}