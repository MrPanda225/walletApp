package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "Comptes")
@Setter
@Getter
public class Compte {
    @Id
    private String num_cpt;

    private Date date_creation;
    private Double solde;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur user;

    @ManyToOne
    @JoinColumn(name = "id_agence")
    private Agence agence;

    @ManyToOne
    @JoinColumn(name = "id_fournisseur")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "id_type_cpt")
    private TypeCpt type_cpt;

   
}
