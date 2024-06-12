package com.walletApp.backend.controller;

import com.walletApp.backend.config.AccountNumberGenerator;
import com.walletApp.backend.model.*;
import com.walletApp.backend.repository.CompteRepository;
import com.walletApp.backend.service.CompteService;
import com.walletApp.backend.service.TypeUtilisateurService;
import com.walletApp.backend.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.walletApp.backend.config.AccountNumberGenerator.*;

import java.util.List;
import java.util.Set;

@Controller
public class web {
    @Autowired
    private TypeUtilisateurService type_user;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(Model model) {
        return helpFunction(model,"index.html");
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profileCount")
    public String profileCount(Model model) {
        return helpFunction(model,"profile.html");
    }

    @GetMapping("/depot")
    public String depot(Model model) {
        return helpFunction(model,"depot.html");
    }

    private String helpFunction(Model model, String url) {

        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("user", utilisateur);

        if (utilisateur == null) {
            return "redirect:/login";
        }

        utilisateur = utilisateurService.findUserWithAccounts(utilisateur.getId_user());

        String photoUrl = utilisateur.getPhotoUrl();
        if (photoUrl == null || photoUrl.isEmpty()) {
            photoUrl = "/uploads/default-profile.jpg"; // Chemin de votre image par défaut
        }

        model.addAttribute("photoUrl", photoUrl);


        Compte premierCompte = compteRepository.findByUser(utilisateur);
        if (premierCompte != null) {
            String formattedAccountNumber = AccountNumberGenerator.formatAccountNumber(premierCompte.getNum_cpt());
            model.addAttribute("formattedAccountNumber", formattedAccountNumber);
            System.out.println(formattedAccountNumber);
        }

        model.addAttribute("compte", premierCompte);



        return url;
    }
}
