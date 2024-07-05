package com.walletApp.backend.controller.API;

import com.walletApp.backend.model.Compte;
import com.walletApp.backend.model.Transaction;
import com.walletApp.backend.service.CompteService;
import com.walletApp.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CompteService compteService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int id, @RequestBody Transaction transactionDetails) {
        Optional<Transaction> optionalTransaction = transactionService.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setDate_trans(transactionDetails.getDate_trans());
            transaction.setMontant_trans(transactionDetails.getMontant_trans());
            transaction.setFrais_trans(transactionDetails.getFrais_trans());
            transaction.setCpt_exp(transactionDetails.getCpt_exp());
            transaction.setCpt_dest(transactionDetails.getCpt_dest());
            transaction.setTypeTransaction(transactionDetails.getTypeTransaction());
            transaction.setStatus(transactionDetails.getStatus());
            transaction.setLieu(transactionDetails.getLieu());
            Transaction updatedTransaction = transactionService.save(transaction);
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        if (transactionService.findById(id).isPresent()) {
            transactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable String accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }

}
