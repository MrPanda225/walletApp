package com.walletApp.backend.controller.API;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.walletApp.backend.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.walletApp.backend.model.*;
import com.walletApp.backend.service.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/Utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService service;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private TypeCompteRepository typeCptRepository;

    @Autowired
    private TypeUtilisateurRepository typeUtilisateurRepository;

    @Autowired
    private HttpSession session;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = service.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        Optional<Utilisateur> utilisateur = service.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur createdUtilisateur = service.createOrUpdateUtilisateur(utilisateur);
        return ResponseEntity.ok(createdUtilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateur) {
        Optional<Utilisateur> existingUtilisateur = service.getUtilisateurById(id);
        if (existingUtilisateur.isPresent()) {
            Utilisateur updatedUtilisateur = service.createOrUpdateUtilisateur(utilisateur);
            return ResponseEntity.ok(updatedUtilisateur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        service.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUtilisateur(@RequestParam("nom") String nom,
                                           @RequestParam("prenoms") String prenoms,
                                           @RequestParam("date_nais") String dateNais,
                                           @RequestParam("sexe") String sexe,
                                           @RequestParam("email") String email,
                                           @RequestParam("phone_number") String phoneNumber,
                                           @RequestParam(value = "photo", required = false) MultipartFile photo,
                                           @RequestParam("password") String password) {

        TypeUtilisateur typeUser = typeUtilisateurRepository.findById(1).orElseThrow(() -> new RuntimeException("Type de user non trouvé"));;

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenoms(prenoms);
        utilisateur.setDate_nais(Date.valueOf(dateNais));
        utilisateur.setSexe(sexe);
        utilisateur.setEmail(email);
        utilisateur.setPhoneNumber(phoneNumber);
        utilisateur.setPassword(password);
        utilisateur.setType_user(typeUser);

        if (photo != null && !photo.isEmpty()) {
            String fileName = fileStorageService.storeFile(photo);
            String fileDownloadUri = "/uploads/" + fileName;
            utilisateur.setPhotoUrl(fileDownloadUri);
        }
        if (utilisateurService.emailExists(email)) {
            return ResponseEntity.badRequest().body("L'email est déjà enregistré.");
        }

        if (utilisateurService.phoneNumberExists(phoneNumber)) {
            return ResponseEntity.badRequest().body("Le numéro de téléphone est déjà enregistré.");
        }

        Utilisateur user = utilisateurService.saveUtilisateur(utilisateur);

        TypeCpt typeCpt = typeCptRepository.findById(1).orElseThrow(() -> new RuntimeException("Type de compte non trouvé"));;

        Compte cpt = utilisateurService.createCompteForUser(utilisateur, null, null, typeCpt);

        session.setAttribute("user", user);
        session.setAttribute("cpt", cpt);

        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/").build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUtilisateur(@RequestParam("email") String email, @RequestParam("password") String password){

        Utilisateur user = utilisateurService.login(email, password);
        if (user == null){
            return ResponseEntity.badRequest().body("Erreur de connexion.");
        }

        Compte cpt = compteRepository.findByUser(user);

        session.setAttribute("user", user);
        session.setAttribute("cpt", cpt);
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/").build();
    }

}
