package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;

    private String nom;
    private String prenoms;
    private Date date_nais;
    private String sexe;
    private String email;
    private String photoUrl;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_type_user")
    private TypeUtilisateur type_user;

}
