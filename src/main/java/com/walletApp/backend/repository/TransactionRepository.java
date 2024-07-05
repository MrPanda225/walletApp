package com.walletApp.backend.repository;

import com.walletApp.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


  @Query("SELECT t FROM Transaction t WHERE t.cpt_exp.num_cpt = :accountId OR t.cpt_dest.num_cpt = :accountId")
  List<Transaction> findTransactionsByAccountId(String accountId);

}
