package com.walletApp.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_utilisateurs")
@Data  // Remplace @Setter et @Getter par @Data pour inclure également toString, equals et hashCode
public class TypeUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_type_user;

    private String lib_type_user;

    // Constructeur par défaut (généré par Lombok)
    public TypeUtilisateur() {}

    // Constructeur avec tous les champs (généré par Lombok)
    public TypeUtilisateur(int id_type_user) {
        this.id_type_user = id_type_user;
    }
}
