package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agences")
@Getter
@Setter
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_agence;

    private String lib_agence;
}
