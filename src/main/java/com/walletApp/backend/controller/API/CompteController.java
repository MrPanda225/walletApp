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

import com.walletApp.backend.model.Compte;
import com.walletApp.backend.model.Utilisateur;
import com.walletApp.backend.service.CompteService;


@RestController
@RequestMapping("/api/compte")
public class CompteController {
    
 @Autowired
    private CompteService service;

    @GetMapping
    public ResponseEntity<List<Compte>> getAllComptes() {
        List<Compte> compte = service.getAllComptes();
        return ResponseEntity.ok(compte);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable String id) {
        Optional<Compte> compte = service.getCompteById(id);
        return compte.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}") // Assurez-vous que l'annotation est PostMapping
    public ResponseEntity<Compte> createCompte(@PathVariable int userId, @RequestBody Compte compte) {
        // Vérifiez que l'ID de l'utilisateur est valide
        if(userId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Vous pouvez maintenant utiliser l'ID de l'utilisateur pour associer le compte
        // à cet utilisateur lors de sa création
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId_user(userId);
        compte.setUser(utilisateur); 

        Compte createdCompte = service.createOrUpdateCompte(compte);
        return ResponseEntity.ok(createdCompte);
    }

    

    @PutMapping("/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable String id, @RequestBody Compte compte) {
        Optional<Compte> existingCompte = service.getCompteById(id);
        if (existingCompte.isPresent()) {
            Compte updatedCompte = service.createOrUpdateCompte(compte);
            return ResponseEntity.ok(updatedCompte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable String id) {
        service.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }

}
