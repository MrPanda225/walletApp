package com.walletApp.backend.controller.API;

import com.walletApp.backend.model.TypeTransaction;
import com.walletApp.backend.service.TypeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/typeTransactions")
public class TypeTransactionController {

    @Autowired
    private TypeTransactionService typeTransactionService;

    @GetMapping
    public List<TypeTransaction> getAllTypeTransactions() {
        return typeTransactionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeTransaction> getTypeTransactionById(@PathVariable int id) {
        Optional<TypeTransaction> typeTransaction = typeTransactionService.findById(id);
        return typeTransaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TypeTransaction createTypeTransaction(@RequestBody TypeTransaction typeTransaction) {
        return typeTransactionService.save(typeTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeTransaction> updateTypeTransaction(@PathVariable int id, @RequestBody TypeTransaction typeTransactionDetails) {
        Optional<TypeTransaction> optionalTypeTransaction = typeTransactionService.findById(id);
        if (optionalTypeTransaction.isPresent()) {
            TypeTransaction typeTransaction = optionalTypeTransaction.get();
            typeTransaction.setLib_type_trans(typeTransactionDetails.getLib_type_trans());
            TypeTransaction updatedTypeTransaction = typeTransactionService.save(typeTransaction);
            return ResponseEntity.ok(updatedTypeTransaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeTransaction(@PathVariable int id) {
        if (typeTransactionService.findById(id).isPresent()) {
            typeTransactionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
