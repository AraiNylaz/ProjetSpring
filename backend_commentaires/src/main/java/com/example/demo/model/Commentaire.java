package com.example.demo.model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;
    private String texte ;
    private int evaluation ;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date_creation ;
    private int utilisateur ;
    private int jeu;
    private String  etat ;


}
