package com.example.demo.controller;

import com.example.demo.model.Commentaire;
import com.example.demo.service.CommentaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/commentaires-secu")
public class CommentaireSecuController {
    private CommentaireService service;

    public CommentaireSecuController(CommentaireService service) { this.service = service; }
    //supprime le commentaire correspondant a l'id
    @DeleteMapping("/{idComment}")
    public void deleteCommentaire(@RequestHeader(name="Authorization") String token, @PathVariable ("idComment") int idComment) {
        service.deleteCommentaire(idComment);
    }
    //update le commentaire
    @PutMapping ("/{idComment}")
    public void updateCommentaire(@RequestHeader(name="Authorization") String token, @PathVariable ("idComment") int idComment, @RequestBody Commentaire commentaire) {
        service.updateCommentaire(idComment,commentaire);
    }
    //ajoute un commentaire
    @PostMapping
    public ResponseEntity<Void> createCommentaire(@RequestHeader(name="Authorization") String token,@RequestBody Commentaire commentaire) {
        Commentaire c = service.saveComm(commentaire);
        if(c == null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(c.getIdComment())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
