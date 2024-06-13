package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.*;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_trans;

    private Date date_trans;
    private Double montant_trans;
    private Double frais_trans;
    private Time time_trans;

    @ManyToOne
    @JoinColumn(name = "cpt_exp")
    private Compte cpt_exp;

    @ManyToOne
    @JoinColumn(name = "cpt_dest")
    private Compte cpt_dest;

    @ManyToOne
    @JoinColumn(name = "id_type_trans")
    private TypeTransaction typeTransaction;

    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "lieu")
    private TypeCpt lieu;
}
