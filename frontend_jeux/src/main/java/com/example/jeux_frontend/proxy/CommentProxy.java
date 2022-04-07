package com.example.jeux_frontend.proxy;

import com.example.jeux_frontend.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="gateway")
@Component
public interface CommentProxy {

    @GetMapping("commentaires/{id}")
    List<Comment> findByJeu(@PathVariable("id") int id);

    @GetMapping("/commentaires/{idJeu}/{idUtilisateur}")
    Iterable<Comment> findByJeuAndUtilisateur(@PathVariable("idJeu") int idJeu, @PathVariable("idUtilisateur") int idUtilisateur);

    @GetMapping("/commentaires/exept/{idJeu}/{idUtilisateur}")
    Iterable<Comment> getCommentaireJeuSaufUtilisateur(@PathVariable("idJeu") int idJeu, @PathVariable("idUtilisateur") int idUtilisateur);

    @GetMapping("/commentaires/getOne/{idCommentaire}")
    Comment findById(@PathVariable("idCommentaire") int idCommentaire);

    @PostMapping("/commentaires-secu")
    Comment createCommentaire(@RequestHeader(name="Authorization") String token,@RequestBody Comment commentaire);

    @GetMapping("/commentaires/moyenne/{id}")
    float moyenneCommentaire(@PathVariable("id") int id);

    @GetMapping("/commentaires/nombreComm/{idJeu}")
    Long getnombreCommentaire(@PathVariable("idJeu") int idJeu);

    @PutMapping("/commentaires-secu/{idComment}")
    void updateCommentaire(@RequestHeader(name="Authorization") String token, @PathVariable("idComment") int idComment, @RequestBody Comment comment);

    @DeleteMapping("/commentaires-secu/{idComment}")
    void deleteCommentaire(@RequestHeader(name="Authorization") String token,@PathVariable("idComment") int idComment);
}

