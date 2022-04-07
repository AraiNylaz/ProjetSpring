package com.example.jeux_frontend.model;

import lombok.Data;

@Data
public class Utilisateur {
    private int id;
    private String prenom;
    private String nom;
    private String email;
}
