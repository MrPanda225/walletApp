package com.walletApp.backend.service;

import com.walletApp.backend.model.Status;
import com.walletApp.backend.model.Transaction;
import com.walletApp.backend.model.Utilisateur;
import com.walletApp.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private StatusService statusService;  // Injecter StatusService, non Status

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(int id) {
        return transactionRepository.findById(id);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteById(int id) {
        transactionRepository.deleteById(id);
    }

    public void validateTransaction(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Mettre à jour le statut de la transaction de 3 à 2
        Optional<Status> confirmedStatus = statusService.findById(2);  // Assurez-vous que le statut 2 est pour "confirmé"
        transaction.setStatus(confirmedStatus.orElseThrow(() -> new RuntimeException("Status not found")));

        // Mettre à jour les soldes des comptes en utilisant actualiseSoldCompte
        boolean debitSuccess = compteService.actualiseSoldCompte(transaction.getCpt_exp().getNum_cpt(), transaction.getMontant_trans(), false);
        boolean creditSuccess = compteService.actualiseSoldCompte(transaction.getCpt_dest().getNum_cpt(), transaction.getMontant_trans(), false);

        if (!debitSuccess || !creditSuccess) {
            throw new RuntimeException("Erreur lors de la mise à jour des soldes des comptes");
        }

        transactionRepository.save(transaction);
    }


}