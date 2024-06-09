package com.walletApp.backend.controller.API;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.walletApp.backend.model.*;
import com.walletApp.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/compte")
public class CompteController {
    
    @Autowired
    private CompteService service;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TypeCompteService typeCompteService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TypeTransactionService typeTransactionService;

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

    @PostMapping("/depot")
    public boolean depot(@RequestParam String cpt_exp, @RequestParam String cpt_des, @RequestParam Double montant) {

        boolean result = service.actualiseSoldCompte(cpt_exp, montant, false) ? service.actualiseSoldCompte(cpt_des, montant, true) : false;
        if (result){

            Optional<Compte> dest = service.getCompteById(cpt_des);
            Optional<Compte> exp = service.getCompteById(cpt_exp);
            Optional<Status> st = statusService.findById(1);
            Optional<TypeCpt> tc = typeCompteService.getTypeCptById(2);
            Optional<TypeTransaction> tt = typeTransactionService.findById(1);

            LocalDate dateCreation = LocalDate.now();
            Double frais = montant * 0.1 ;


            Transaction transaction = new Transaction();
            transaction.setCpt_dest(dest.orElseGet(null));
            transaction.setCpt_exp(exp.orElseGet(null));
            transaction.setStatus(st.orElseGet(null));
            transaction.setLieu(tc.orElseGet(null));
            transaction.setTypeTransaction(tt.orElseGet(null));
            transaction.setDate_trans(Date.valueOf(dateCreation));
            transaction.setFrais_trans(frais);
            transaction.setMontant_trans(montant);

            transactionService.save(transaction);
        }
        return result;
    }

}
