package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name = "status")
@Setter
@Getter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_status;

    private String lib_status;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private Transaction trans;

}
