package com.example.jeux.controller;

import com.example.jeux.model.Jeux;
import com.example.jeux.service.JeuxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/jeux")
public class JeuxController {

    private JeuxService service;

    public JeuxController(JeuxService jeuxService){

        this.service=jeuxService;
    }

    private boolean testPrices(double priceMin,double priceMax){
        if(((priceMin != -1 && priceMax!=-1) || (priceMin != -1 && priceMax==-1) || (priceMin==-1 && priceMax!=-1))){
            return true;
        }
        return false;
    }

    @GetMapping
    public Iterable<Jeux> listAllJeux(@RequestParam(required = false)String category,@RequestParam(required = false,defaultValue = "-1.0") double priceMin,@RequestParam(required = false,defaultValue = "-1.0") double priceMax,@RequestParam(required=false)String direction){
        if(category==null && testPrices(priceMin,priceMax)){
            return service.jeuxFilteredByPrice(priceMin,priceMax,direction);
        }

        if(category!=null && testPrices(priceMin,priceMax)  ){
            return service.jeuxFilteredByPriceAndCategory(category,priceMin,priceMax,direction);
        }

        if(category !=null && priceMin ==-1 && priceMax==-1){
            return service.findByCategorie(category,direction);
        }
        System.out.println(direction);
        return service.findAllJeux(direction);
    }

    @GetMapping("/{id}")
    public Jeux getJeux(@PathVariable("id") int id){
        return service.getJeux(id);
    }

}
