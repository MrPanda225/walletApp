package com.walletApp.backend.repository;

import com.walletApp.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

   // List<Transaction> findByCptExp_NumCptOrCptDest_NumCpt(int cptExpNumCpt);

  int a = 2 ;

  @Query("SELECT t FROM Transaction t WHERE t.cpt_exp.user.id_user = :userId OR t.cpt_dest.user.id_user = :userId")
  List<Transaction> findTransactionsByUserId(int userId);
}
