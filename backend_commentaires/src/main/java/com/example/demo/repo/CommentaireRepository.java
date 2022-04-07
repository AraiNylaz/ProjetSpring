package com.example.demo.repo;
import com.example.demo.model.Commentaire ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends CrudRepository<Commentaire, Integer>  {
    //donne tout les commentaires d'un jeu
    List<Commentaire> findAllByJeu(int jeu);

    List<Commentaire> findByJeu(int idJeu);
    //donne tout les commentaires d'un jeu avec un utilisateur
    List<Commentaire> findByJeuAndUtilisateur(int idJeu , int idUtilisateur);
    //compte le nombre de commentaire d'un jeu
    long countByJeuEquals(int jeu);
    //liste tout les comm d'un jeux sauf d'un user
    List<Commentaire> findByJeuAndUtilisateurNot(int jeu, int utilisateur);
    //donne la moyenne des evalutation pour un jeu
    @Query("SELECT AVG(evaluation) FROM commentaires WHERE jeu = ?1")
    float averageOfEvaluation(int jeu);

}
