package com.walletApp.backend.service;

import java.util.List;
import java.util.Optional;

import com.walletApp.backend.model.Agence;
import com.walletApp.backend.model.TypeCpt;
import com.walletApp.backend.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walletApp.backend.model.Compte;
import com.walletApp.backend.repository.CompteRepository;


@Service
public class CompteService {
 
    @Autowired
    private CompteRepository repository;

    @Autowired
    private AgenceRepository agenceRepository;

    public List<Compte> getAllComptes() {
        return repository.findAll();
    }

    public Optional<Compte> getCompteById(String id) {
        return repository.findById(id);
    }

    public Compte createOrUpdateCompte(Compte compte) {
        return repository.save(compte);
    }

    public void deleteCompte(String id) {
        repository.deleteById(id);
    }

    public Compte findByAgenceId(int agenceId) {
        Optional<Agence> agence = agenceRepository.findById(agenceId);
        return repository.findByAgence(agence.orElse(null));
    }


    public boolean actualiseSoldCompte(String numCpt, double montant, boolean op) {
        Optional<Compte> compteOptional = repository.findById(numCpt);

        if (compteOptional.isPresent()) {
            Compte compte = compteOptional.get();
            if (op){
                compte.setSolde(compte.getSolde() + montant);
            }else {
                if (compte.getSolde() >= montant) {
                    compte.setSolde(compte.getSolde() - montant);
                }else {
                    return false;
                }
            }
            repository.save(compte);
            return true;
        } else {
            return false;
        }
    }




    public boolean transfererFonds(String compteSourceNumero, String compteDestinationNumero, double montant) {
        Optional<Compte> compteSourceOptional = getCompteById(compteSourceNumero);
        Optional<Compte> compteDestinationOptional = getCompteById(compteDestinationNumero);

        if (compteSourceOptional.isPresent() && compteDestinationOptional.isPresent()) {
            Compte compteSource = compteSourceOptional.get();
            Compte compteDestination = compteDestinationOptional.get();

            if (compteSource.getSolde() >= montant) {
                compteSource.setSolde(compteSource.getSolde() - montant);
                compteDestination.setSolde(compteDestination.getSolde() + montant);
                repository.save(compteSource);
                repository.save(compteDestination);
                return true;
            }
        }
        return false;
    }

    public List<Compte> getByTypeCpt(TypeCpt typeCpt){
        return repository.findByTypecpt(typeCpt);
    }
}
