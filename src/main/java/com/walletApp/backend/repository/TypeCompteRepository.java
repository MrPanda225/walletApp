package com.walletApp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walletApp.backend.model.TypeCpt;

@Repository
public interface TypeCompteRepository extends JpaRepository<TypeCpt, Integer> {
}
