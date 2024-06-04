package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fournisseurs")
@Getter
@Setter
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_fournisseur;

    private String lib_fournisseur;
}
