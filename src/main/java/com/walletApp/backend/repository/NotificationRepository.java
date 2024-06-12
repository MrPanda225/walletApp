package com.walletApp.backend.repository;

import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsRead(Utilisateur user, boolean isRead);
}
