package com.example.demo.proxi;


import com.example.demo.model.Game;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="gateway")
@Component
public interface GameProxy {
    //afficher tout les jeux
    @GetMapping("/jeux")
    Iterable<Game> findAll(@RequestParam(required=false) String category,
                           @RequestParam(required=false) Double priceMin,
                           @RequestParam(required=false) Double priceMax,
                           @RequestParam(required=false) String direction);

    //filtrer par catégorie
    @GetMapping("/jeux?categorie={categorie}")
    Iterable<Game>findAllWithCategorie(@PathVariable String categorie);
    //touver un jeu avec id
    @GetMapping("/jeux/{id}")
    Game findByid(@PathVariable int id);
    //prix min et max
    @GetMapping("/jeux?prixMin={min}&prixMax={max}")
    Iterable<Game>findAllWithMinAndMaxPrice(@PathVariable int min,@PathVariable int max);

    //update un jeux
    @PutMapping("/jeux-secu/{id}")
    void updateGame(@RequestHeader(name="Authorization") String token,@PathVariable int id,@RequestBody Game gameToUpdate);
    //supprimer jeux
    @DeleteMapping("/jeux-secu/{id}")
    void deleteGame(@RequestHeader(name="Authorization") String token,@PathVariable int id);
    //ajouter jeux
    @PostMapping("/jeux-secu")
    void saveGame(@RequestHeader(name="Authorization") String token,@RequestBody Game gameToSave);

}
