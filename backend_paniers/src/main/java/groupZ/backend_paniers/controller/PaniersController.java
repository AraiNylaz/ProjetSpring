package groupZ.backend_paniers.controller;

import groupZ.backend_paniers.model.Paniers;
import groupZ.backend_paniers.service.PaniersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;


@RestController
@RequestMapping("/paniers")
public class PaniersController {

    private PaniersService service;

    public PaniersController(PaniersService service) { this.service = service; }

    @GetMapping
    public Iterable<Paniers> getAllPaniersUser(@RequestHeader(name = "Authorization", required =true) String token){
       return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> createPanier(@RequestHeader(name = "Authorization", required =true) String token, @RequestBody Paniers panier) { //ajoute un panier a la db
        Paniers p = service.savePanier(panier);
        if(p == null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deletePanier(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id) {
        service.deletePanier(id);
    }

    @PutMapping("/{userId}/{productId}")
    public void updatePanier(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("userId") int userId, @PathVariable("productId") int productId, @RequestBody Paniers paniers) {
        service.updatePanier(paniers);
    }

    @GetMapping("/{id}")
    public Iterable<Paniers> getPanier(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id) {
        return service.findByUserId(id);
    }

    @GetMapping("/{userId}/{id}")
    public Paniers getPanier(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("userId") int userId, @PathVariable("id") int id){
        return service.findPanier(id);
    }

}