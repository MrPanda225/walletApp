package com.walletApp.backend.service;

import com.walletApp.backend.model.Services;
import com.walletApp.backend.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServService {

 @Autowired
    private ServicesRepository repository;

    public List<Services> getAllServices() {
        return repository.findAll();
    }

    public Optional<Services> getServicesById(int id) {
        return repository.findById(id);
    }

    public Services addOrUpdateServices(Services service) {
        return repository.save(service);
    }

    public void deleteServices(int id) {
        repository.deleteById(id);
    }


}
