package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_utilisateurs")
@Setter
@Getter
public class TypeUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_type_user;

    private String lib_type_user;
}
