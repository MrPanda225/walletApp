package com.walletApp.backend.repository;

import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Transaction;
import com.walletApp.backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsRead(Utilisateur user, boolean isRead);
    Optional<Notification> findByTransaction(Transaction transaction);
}
