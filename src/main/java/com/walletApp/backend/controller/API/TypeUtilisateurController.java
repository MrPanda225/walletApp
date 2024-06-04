package com.walletApp.backend.controller.API;

import com.walletApp.backend.model.TypeUtilisateur;
import com.walletApp.backend.service.TypeUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/typeUtilisateurs")
public class TypeUtilisateurController {

    @Autowired
    private TypeUtilisateurService service;

    @GetMapping
    public ResponseEntity<List<TypeUtilisateur>> getAllTypeUtilisateurs() {
        List<TypeUtilisateur> typeUtilisateurs = service.getAllTypeUtilisateurs();
        return ResponseEntity.ok(typeUtilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeUtilisateur> getTypeUtilisateurById(@PathVariable int id) {
        Optional<TypeUtilisateur> typeUtilisateur = service.getTypeUtilisateurById(id);
        return typeUtilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeUtilisateur> createTypeUtilisateur(@RequestBody TypeUtilisateur typeUtilisateur) {
        TypeUtilisateur createdTypeUtilisateur = service.createOrUpdateTypeUtilisateur(typeUtilisateur);
        return ResponseEntity.ok(createdTypeUtilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeUtilisateur> updateTypeUtilisateur(@PathVariable int id, @RequestBody TypeUtilisateur typeUtilisateur) {
        Optional<TypeUtilisateur> existingTypeUtilisateur = service.getTypeUtilisateurById(id);
        if (existingTypeUtilisateur.isPresent()) {
            TypeUtilisateur updatedTypeUtilisateur = service.createOrUpdateTypeUtilisateur(typeUtilisateur);
            return ResponseEntity.ok(updatedTypeUtilisateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeUtilisateur(@PathVariable int id) {
        service.deleteTypeUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}