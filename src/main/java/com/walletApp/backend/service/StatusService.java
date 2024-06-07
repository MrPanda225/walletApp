package com.walletApp.backend.service;

import com.walletApp.backend.model.Status;
import com.walletApp.backend.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Optional<Status> findById(int id) {
        return statusRepository.findById(id);
    }

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public void deleteById(int id) {
        statusRepository.deleteById(id);
    }
}
