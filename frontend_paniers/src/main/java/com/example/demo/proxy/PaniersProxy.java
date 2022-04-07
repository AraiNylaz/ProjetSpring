package com.example.demo.proxy;

import com.example.demo.model.Paniers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "gateway")
public interface PaniersProxy {

    @GetMapping("/paniers/{id}")
    Iterable<Paniers> findByUserId(@RequestHeader(name="Authorization") String token,@PathVariable("id") int id);

    @GetMapping("/paniers")
    Iterable<Paniers> findAll(@RequestHeader(name="Authorization") String token);

    @PostMapping("/paniers")
    void savePanier(@RequestHeader(name="Authorization") String token,@RequestBody Paniers panier);

    @PutMapping("/paniers/{userId}/{productId}")
    void updatePanier(@RequestHeader(name="Authorization") String token,@PathVariable int userId, @PathVariable int productId,
                      @ModelAttribute Paniers panier);

    @DeleteMapping("/paniers/{id}")
    void deletePanier(@RequestHeader(name="Authorization") String token,@PathVariable int id);

    @GetMapping("/paniers/{userId}/{id}")
    Paniers getPanier(@RequestHeader(name="Authorization") String token,@PathVariable int userId, @PathVariable int id);

}
