package com.walletApp.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAdmin {

    @GetMapping("/administration")
    public String loginAdmin(Model model) {
        return "/admin/index.html";
    }

    @GetMapping("/administration/")
    public String homeAdmin(Model model) {
        return "/admin/home.html";
    }
}
