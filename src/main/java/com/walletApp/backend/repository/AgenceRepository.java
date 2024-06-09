package com.walletApp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletApp.backend.model.Agence;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer> {
    Agence findByUsernameAndPassword(String username, String password);
}
