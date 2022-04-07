package com.example.demo.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String email;
    private boolean estAdministrateur;

    public UserDTO(int id, String nom, String prenom, LocalDate dateNaissance, String adresse, String email, boolean estAdministrateur) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.email = email;
        this.estAdministrateur = estAdministrateur;
    }
}

