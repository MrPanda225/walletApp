package com.walletApp.backend.controller;

import com.walletApp.backend.model.TypeUtilisateur;
import com.walletApp.backend.service.TypeUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class web {
    @Autowired
    private TypeUtilisateurService type_user;

    @GetMapping("/")
    public String index(Model model) {
        return "index.html";
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<TypeUtilisateur> typesUtilisateur = type_user.getAllTypeUtilisateurs();
        model.addAttribute("typesUtilisateur", typesUtilisateur);
        return "register.html";
    }
}
