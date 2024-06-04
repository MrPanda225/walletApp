package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_cpts")
@Getter
@Setter
public class TypeCpt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id_type_cpt;

    private  String lib_type_cpt;
}
