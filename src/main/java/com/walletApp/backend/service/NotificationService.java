package com.walletApp.backend.service;

import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Transaction;
import com.walletApp.backend.model.Utilisateur;
import com.walletApp.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(Utilisateur user, String message, Transaction transaction) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setTransaction(transaction);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications(Utilisateur user) {
        List<Notification> notifications = notificationRepository.findByUserAndIsRead(user, false);
        return notifications.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private Notification convertToDTO(Notification notification) {
        Notification dto = new Notification();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setTransaction(notification.getTransaction() != null ? notification.getTransaction() : null);
        return dto;
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification pas vue"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
