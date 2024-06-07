package com.walletApp.backend.repository;

import com.walletApp.backend.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeTransactionRepository extends JpaRepository<TypeTransaction, Integer> {
}
