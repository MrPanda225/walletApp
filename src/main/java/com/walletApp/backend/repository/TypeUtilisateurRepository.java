package com.walletApp.backend.repository;

import com.walletApp.backend.model.TypeUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUtilisateurRepository extends JpaRepository<TypeUtilisateur, Integer> {
}
