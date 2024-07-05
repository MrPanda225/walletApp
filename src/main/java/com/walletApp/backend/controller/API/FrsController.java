package com.walletApp.backend.controller.API;

import java.util.List;
import java.util.Optional;

import com.walletApp.backend.model.Services;
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

import com.walletApp.backend.model.Fournisseur;
import com.walletApp.backend.service.FrsService;


@RestController
@RequestMapping("/api/frs")
public class FrsController {
    
@Autowired
    private FrsService service;

    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFrs() {
        List<Fournisseur> frs = service.getAllFrs();
        return ResponseEntity.ok(frs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable int id) {
        Optional<Fournisseur> frs = service.getFournisseurById(id);
        return frs.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<Services>> getServicesByFournisseurId(@PathVariable int id) {
        Optional<Fournisseur> fournisseur = service.getFournisseurById(id);
        if (fournisseur.isPresent()) {
            List<Services> services = fournisseur.get().getServicesFournis();
            return ResponseEntity.ok(services);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur frs) {
        Fournisseur createdFournisseur = service.createOrUpdateFournisseur(frs);
        return ResponseEntity.ok(createdFournisseur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFrs(@PathVariable int id, @RequestBody Fournisseur frs) {
        Optional<Fournisseur> existingFournisseur = service.getFournisseurById(id);
        if (existingFournisseur.isPresent()) {
            Fournisseur updatedFournisseur = service.createOrUpdateFournisseur(frs);
            return ResponseEntity.ok(updatedFournisseur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable int id) {
        service.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }


}
