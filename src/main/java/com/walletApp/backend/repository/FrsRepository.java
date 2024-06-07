package com.walletApp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletApp.backend.model.Fournisseur;

@Repository
public interface FrsRepository extends JpaRepository<Fournisseur, Integer> {
    
}