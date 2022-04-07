package com.example.demo.proxy;

import com.example.demo.model.Jeux;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "gateway")
public interface JeuxProxy {

    @GetMapping("/jeux/{id}")
    Jeux findById(@PathVariable("id") int id);

    @GetMapping("/jeux")
    Iterable<Jeux> findAll(@RequestParam(required=false) String category,
                           @RequestParam(required=false) Double priceMin,
                           @RequestParam(required=false) Double priceMax,
                           @RequestParam(required=false) String direction);
}
