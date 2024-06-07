package com.walletApp.backend.service;

import com.walletApp.backend.model.TypeTransaction;
import com.walletApp.backend.repository.TypeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeTransactionService {

    @Autowired
    private TypeTransactionRepository typeTransactionRepository;

    public List<TypeTransaction> findAll() {
        return typeTransactionRepository.findAll();
    }

    public Optional<TypeTransaction> findById(int id) {
        return typeTransactionRepository.findById(id);
    }

    public TypeTransaction save(TypeTransaction typeTransaction) {
        return typeTransactionRepository.save(typeTransaction);
    }

    public void deleteById(int id) {
        typeTransactionRepository.deleteById(id);
    }
}
