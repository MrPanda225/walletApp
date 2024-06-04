package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status")
@Setter
@Getter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_status;

    private String lib_status;
}
