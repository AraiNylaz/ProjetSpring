package com.example.demo.service;


import com.example.demo.model.Commentaire;
import com.example.demo.repo.CommentaireRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentaireService {
    private final CommentaireRepository repo;
    public CommentaireService(CommentaireRepository repo) {
        this.repo = repo;
    }

    //donne tout les commentaires d'un jeu
    public Iterable<Commentaire> findByJeu(int id){
        return repo.findAllByJeu(id);
    }
    //donne tout les commentaires d'un jeu avec un utilisateur
    public Iterable<Commentaire> findByJeuAndUtilisateur(int idJeu , int idUtilisateur){
        return repo.findByJeuAndUtilisateur(idJeu,idUtilisateur);
    }
    //donne le commentaire correspondant a l'id
    public Optional<Commentaire> findById(int id) {
        return repo.findById(id);
    }
    //delete le commentaire correspondant a l'id
    public void deleteCommentaire(int id){ repo.deleteById(id);}
    //update le commentaire
    public Commentaire updateCommentaire( int id,Commentaire commentaire) {
        Commentaire c = repo.findById(id).orElseThrow(InternalError::new);
        c.setTexte(commentaire.getTexte());
        c.setEtat(commentaire.getEtat());
        return repo.save(c);
    }
    //ajoute un commentaire
    public Commentaire saveComm(Commentaire commentaire) {
        return repo.save(commentaire);
    }
    //compte le nombre de commentaire d'un jeu
    public long countComm(int jeu){
        return repo.countByJeuEquals(jeu);
    }
    //aficher tout les comm d'un jeu sauf d'un user
    public Iterable<Commentaire> findByJeuSaufUtilisateur(int idJeu , int idUtilisateur){
        return repo.findByJeuAndUtilisateurNot(idJeu,idUtilisateur);
    }
    //moyenne note comm
    public float moyenneEvaluation(int jeu){
        System.out.println(repo.averageOfEvaluation(jeu));
        return repo.averageOfEvaluation(jeu);}

    public Iterable<Commentaire> findAll() {
        return repo.findAll();
    }
}






