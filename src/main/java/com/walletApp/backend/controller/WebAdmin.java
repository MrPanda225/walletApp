package com.walletApp.backend.controller;

import com.walletApp.backend.model.*;
import com.walletApp.backend.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebAdmin {
    @Autowired
    private AgenceService agenceService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private HttpSession session;

    @GetMapping("/administration")
    public String loginAdmin(Model model) {
        return "/admin/index.html";
    }

    @GetMapping("/administration/")
    public String homeAdmin(Model model) {
        return helpFunctionAmin(model, "/admin/home.html");
    }

    @GetMapping("/administration/transaction")
    public String gereTransaction(Model model) {
        return helpFunctionAmin(model, "/admin/gereTransaction.html");
    }

    @GetMapping("/administration/client")
    public String gereClient(Model model) {
        return helpFunctionAmin(model, "/admin/gereClient.html");
    }

    @GetMapping("/administration/depot")
    public String depotAdmin(Model model) {
        return helpFunctionAmin(model, "/admin/depoAgenceToClient.html");
    }

    @GetMapping("/administration/retrait")
    public String retraitAdmin(Model model) {
        return helpFunctionAmin(model, "/admin/retraitAgenceToClient.html");
    }

    @GetMapping("/administration/addAgence")
    public String addAgenceAdmin(Model model) {
        List<Agence> agences = agenceService.getAllAgences();
        Map<Integer, Double> agenceSoldes = new HashMap<>();

        for (Agence agence : agences) {
            Compte compte = compteService.findByAgenceId(agence.getId_agence());
            if (compte != null) {
                agenceSoldes.put(agence.getId_agence(), compte.getSolde());
            } else {
                agenceSoldes.put(agence.getId_agence(), 0.0);
            }
        }

        model.addAttribute("agences", agences);
        model.addAttribute("agenceSoldes", agenceSoldes);
        return helpFunctionAmin(model,"/admin/creerAgence.html");
    }

    @GetMapping("/logoutAdmin")
    public String logoutAdmin(HttpSession session) {
        session.invalidate();
        return "redirect:/administration";
    }

    private String helpFunctionAmin(Model model, String url){
        Compte cpt = (Compte) session.getAttribute("cpt");

        if (cpt != null && cpt.getTypecpt().getId_type_cpt() == 2){
            Agence agence = (Agence) session.getAttribute("agence");
            List<Transaction> trs = transactionService.getTransactionsByAccountId(cpt.getNum_cpt());
            model.addAttribute("size_transactions", trs.size());
            model.addAttribute("list_transaction", trs);
            model.addAttribute("agence" ,agence);
            model.addAttribute("exp", cpt.getNum_cpt());
            model.addAttribute("cpt", cpt);
        }else {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur == null){
                return "redirect:/administration";
            }
            model.addAttribute("tUser", utilisateur.getType_user().getId_type_user());
            model.addAttribute("tUserLib", utilisateur.getType_user().getLib_type_user());
            model.addAttribute("user", utilisateur);
        }
        return url;
    }
}
