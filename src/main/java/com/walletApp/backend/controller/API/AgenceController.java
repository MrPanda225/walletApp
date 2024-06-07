package com.walletApp.backend.controller.API;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walletApp.backend.model.Agence;
import com.walletApp.backend.service.AgenceService;


@RestController
@RequestMapping("/api/agence")
public class AgenceController {
    
     @Autowired
    private AgenceService service;

    @GetMapping
    public ResponseEntity<List<Agence>> getAllAgences() {
        List<Agence> agence = service.getAllAgences();
        return ResponseEntity.ok(agence);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable int id) {
        Optional<Agence> agence = service.getAgenceById(id);
        return agence.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) {
        Agence createdAgence = service.createOrUpdateAgence(agence);
        return ResponseEntity.ok(createdAgence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> updateAgent(@PathVariable int id, @RequestBody Agence agence) {
        Optional<Agence> existingAgence = service.getAgenceById(id);
        if (existingAgence.isPresent()) {
            Agence updatedAgence = service.createOrUpdateAgence(agence);
            return ResponseEntity.ok(updatedAgence);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable int id) {
        service.deleteAgence(id);
        return ResponseEntity.noContent().build();
    }

}
