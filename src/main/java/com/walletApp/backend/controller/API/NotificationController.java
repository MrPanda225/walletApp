package com.walletApp.backend.controller.API;

import com.walletApp.backend.controller.API.NotificationController;
import com.walletApp.backend.model.Notification;
import com.walletApp.backend.model.Utilisateur;
import com.walletApp.backend.service.NotificationService;
import com.walletApp.backend.service.TransactionService;
import com.walletApp.backend.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@RequestParam int userId) {
        Optional<Utilisateur> user = utilisateurService.getUtilisateurById(userId);
        if (user.isPresent()) {
            List<Notification> notifications = notificationService.getUnreadNotifications(user.get());
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/validation/{transactionId}")
    public ResponseEntity<Void> validateTransaction(@PathVariable int transactionId) {
        transactionService.validateTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancel/{transactionId}")
    public ResponseEntity<Void> cancelTransaction(@PathVariable int transactionId) {
            transactionService.cancelTransaction(transactionId);
            return ResponseEntity.noContent().build();

    }
}
