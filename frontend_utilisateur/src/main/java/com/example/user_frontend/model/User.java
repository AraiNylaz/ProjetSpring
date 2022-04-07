package com.example.user_frontend.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class User {

    private int id;
    private String nom;
    private String prenom;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateNaissance;
    private String adresse;
    private String email;
    private String motDePasse;
    private boolean estAdministrateur;
    private String pseudo;

    public User(int id, String nom, String prenom, LocalDate dateNaissance, String adresse, String email, String motDePasse, boolean estAdministrateur, String pseudo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.email = email;
        this.motDePasse = motDePasse;
        this.estAdministrateur = estAdministrateur;
        this.pseudo = pseudo;
    }
}
