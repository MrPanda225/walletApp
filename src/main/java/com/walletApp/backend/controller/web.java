package com.walletApp.backend.controller;

import com.walletApp.backend.model.*;
import com.walletApp.backend.service.TypeUtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class web {
    @Autowired
    private TypeUtilisateurService type_user;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        Compte cpt = (Compte) session.getAttribute("cpt");
        model.addAttribute("user", utilisateur);
        return "index.html";
    }

  /*  @GetMapping("/register")
    public String register(Model model) {
        List<TypeUtilisateur> typesUtilisateur = type_user.getAllTypeUtilisateurs();
        model.addAttribute("typesUtilisateur", typesUtilisateur);
        return "register.html";
    }*/

    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup.html";
    }
}
