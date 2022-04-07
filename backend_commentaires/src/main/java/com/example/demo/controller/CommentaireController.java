package com.example.demo.controller;
import com.example.demo.model.Commentaire;
import com.example.demo.service.CommentaireService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private CommentaireService service;

    public CommentaireController(CommentaireService service) { this.service = service; }
    //donne tout les commentaires d'un produit
    @GetMapping("/{idJeu}")
    public Iterable<Commentaire> getCommentaireJeu(@PathVariable ("idJeu") int idJeu) {
        return service.findByJeu(idJeu);
    }
    //donne tout les commentaires d'un jeu avec un utilisateur
    @GetMapping("/{idJeu}/{idUtilisateur}")
    public Iterable<Commentaire> getCommentaireJeuUtilisateur(@PathVariable ("idJeu") int idJeu, @PathVariable("idUtilisateur") int idUtilisateur) {
        return service.findByJeuAndUtilisateur(idJeu,idUtilisateur);
    }
    //donne le commentaire correspondant a l'id
    @GetMapping("/getOne/{idComment}")
    public Optional<Commentaire> getOneCommentaire(@PathVariable ("idComment") int idComment) {
        return service.findById(idComment);
    }

    @GetMapping("/nombreComm/{idJeu}")
    public Long getnombreCommentaire(@PathVariable ("idJeu") int idJeu) {
        return service.countComm(idJeu);
    }

    //aficher tout les comm d'un jeu sauf d'un user
    @GetMapping("/exept/{idJeu}/{idUtilisateur}")
    public Iterable<Commentaire> getCommentaireJeuSaufUtilisateur(@PathVariable ("idJeu") int idJeu, @PathVariable("idUtilisateur") int idUtilisateur) {
        return service.findByJeuSaufUtilisateur(idJeu,idUtilisateur);
    }
    //moyenne note comm
    @GetMapping ("/moyenne/{id}")
    public float moyenneCommentaire(@PathVariable ("id") int id) {
        return service.moyenneEvaluation(id);
    }


}
