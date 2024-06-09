package com.walletApp.backend.repository;

import com.walletApp.backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByEmail(String email);
    Utilisateur findByPhoneNumber(String phoneNumber);
    Utilisateur findByEmailAndPassword(String email, String password);
    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.comptes WHERE u.id_user = :userId")
    Utilisateur findByIdWithComptes(@Param("userId") int userId);
}
