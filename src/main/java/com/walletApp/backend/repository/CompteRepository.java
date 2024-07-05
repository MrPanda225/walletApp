package com.walletApp.backend.repository;

import com.walletApp.backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    Compte findByUser(Utilisateur user);
    Compte findByAgence(Agence agence);
    List<Compte> findByTypecpt(TypeCpt typeCpt);
    //Optional<Compte> findByNumeroCompte(String numeroCompte);
}