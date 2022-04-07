package com.example.jeux_frontend.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Comment {
    private int idComment ;
    private String texte;
    private int evaluation;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date_creation;
    private int utilisateur;
    private int jeu;
    private String etat;

    public Comment(int idComment, String texte, int evaluation, LocalDate date_creation, int utilisateur, int jeu, String etat) {
        this.idComment = idComment;
        this.texte = texte;
        this.evaluation = evaluation;
        this.date_creation = date_creation;
        this.utilisateur = utilisateur;
        this.jeu = jeu;
        this.etat = etat;
    }
}
