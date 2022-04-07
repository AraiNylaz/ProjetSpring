package com.example.jeux.controller;

import com.example.jeux.model.Jeux;
import com.example.jeux.service.JeuxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/jeux-secu")
public class JeuxControllerSecu {

    private JeuxService service;

    public JeuxControllerSecu(JeuxService jeuxService){

        this.service=jeuxService;
    }

    @PostMapping
    public ResponseEntity<Void> addJeux(@RequestHeader(name="Authorization") String token,@RequestBody Jeux jeux){
        Jeux newJeux=service.addJeux(jeux);
        if(newJeux==null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newJeux.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteJeux(@RequestHeader(name="Authorization") String token,@PathVariable("id") int id){
        service.deleteJeuxById(id);
    }

    @PutMapping("/{id}")
    public Jeux editJeux(@RequestHeader(name="Authorization") String token,@PathVariable("id") int id,@RequestBody Jeux jeux){
        return service.updateJeux(id,jeux);
    }
}
