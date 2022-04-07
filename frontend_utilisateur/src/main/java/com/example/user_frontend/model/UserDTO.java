package com.example.user_frontend.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserDTO {

    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String email;
    private boolean estAdministrateur;
    private String pseudo;

    public UserDTO(int id, String nom, String prenom, LocalDate dateNaissance, String adresse, String email, boolean estAdministrateur, String pseudo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.email = email;
        this.estAdministrateur = estAdministrateur;
        this.pseudo = pseudo;
    }
}

