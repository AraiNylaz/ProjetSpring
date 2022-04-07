package com.example.user_backend.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity(name="utilisateurs")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dateNaissance;
    private String adresse;

    @Column(unique = true)
    private String email;
    private String motDePasse;
    private boolean estAdministrateur;

    private String pseudo;

}
