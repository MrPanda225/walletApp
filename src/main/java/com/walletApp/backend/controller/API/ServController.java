package com.walletApp.backend.controller.API;

import com.walletApp.backend.model.Services;
import com.walletApp.backend.service.ServService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServController {

    @Autowired
    private ServService servService;

    @GetMapping
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = servService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable("id") int id) {
        Optional<Services> service = servService.getServicesById(id);
        return service.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Services> addService(@RequestBody Services service) {
        Services newService = servService.addOrUpdateServices(service);
        return new ResponseEntity<>(newService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Services> updateService(@PathVariable("id") int id, @RequestBody Services service) {
        service.setId_service(id); // Assurez-vous que l'ID du service est défini correctement pour la mise à jour
        Services updatedService = servService.addOrUpdateServices(service);
        return new ResponseEntity<>(updatedService, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") int id) {
        Optional<Services> service = servService.getServicesById(id);
        if (service.isPresent()) {
            servService.deleteServices(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
