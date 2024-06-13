package com.walletApp.backend.repository;

import com.walletApp.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

   // List<Transaction> findByCptExp_NumCptOrCptDest_NumCpt(int cptExpNumCpt);

  int a = 2 ;
}
