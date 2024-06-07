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

import com.walletApp.backend.model.TypeCpt;
import com.walletApp.backend.service.TypeCompteService;

@RestController
@RequestMapping("/api/typeCpt")
public class TypeCompteController {

    @Autowired
    private TypeCompteService service;

    @GetMapping
    public ResponseEntity<List<TypeCpt>> getAllTypeComptes() {
        List<TypeCpt> typeComptes = service.getAllTypeComptes();
        return ResponseEntity.ok(typeComptes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeCpt> getTypeCompteById(@PathVariable int id) {
        Optional<TypeCpt> typeCompte = service.getTypeCptById(id);
        return typeCompte.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeCpt> createTypeUtilisateur(@RequestBody TypeCpt typeCpt) {
        TypeCpt createdTypeCpt = service.createOrUpdateTypeUtilisateur(typeCpt);
        return ResponseEntity.ok(createdTypeCpt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeCpt> updateTypeCpt(@PathVariable int id, @RequestBody TypeCpt typeCpt) {
        Optional<TypeCpt> existingTypeCpt = service.getTypeCptById(id);
        if (existingTypeCpt.isPresent()) {
            TypeCpt updatedTypeCpt = service.createOrUpdateTypeUtilisateur(typeCpt);
            return ResponseEntity.ok(updatedTypeCpt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeCpt(@PathVariable int id) {
        service.deleteTypeCompte(id);
        return ResponseEntity.noContent().build();
    }
    
}
