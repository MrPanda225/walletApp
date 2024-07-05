package com.walletApp.backend.service;

import com.walletApp.backend.model.Compte;
import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Status;
import com.walletApp.backend.model.Transaction;
import com.walletApp.backend.repository.NotificationRepository;
import com.walletApp.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private NotificationRepository notifRepository;

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
                .orElseThrow(() -> new RuntimeException("Aucune transaction trouvée"));

        // Mettre à jour le statut de la transaction de 3 à 1
        Optional<Status> confirmedStatus = statusService.findById(1);
        transaction.setStatus(confirmedStatus.orElseThrow(() -> new RuntimeException("Status non trouvé")));

        // Mettre à jour les soldes des comptes en utilisant actualiseSoldCompte
        boolean debitSuccess = compteService.actualiseSoldCompte(transaction.getCpt_exp().getNum_cpt(), transaction.getMontant_trans(), false);
        boolean creditSuccess = compteService.actualiseSoldCompte(transaction.getCpt_dest().getNum_cpt(), transaction.getMontant_trans(), false);

        if (!debitSuccess || !creditSuccess) {
            throw new RuntimeException("Erreur lors de la mise à jour des soldes des comptes");
        }

        transactionRepository.save(transaction);

        Notification notification = notifRepository.findByTransaction(transaction)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notifRepository.save(notification);
    }

    public void cancelTransaction(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Aucune transaction trouvée"));

        // Mettre à jour le statut de la transaction à un statut annulé (2)
        Optional<Status> cancelledStatus = statusService.findById(2);
        transaction.setStatus(cancelledStatus.orElseThrow(() -> new RuntimeException("Status non trouvé")));

        transactionRepository.save(transaction);

        Notification notification = notifRepository.findByTransaction(transaction)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notifRepository.save(notification);
    }

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findTransactionsByAccountId(accountId);
    }


}