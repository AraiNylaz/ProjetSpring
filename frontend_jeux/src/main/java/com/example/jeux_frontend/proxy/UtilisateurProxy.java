package com.example.jeux_frontend.proxy;

import com.example.jeux_frontend.model.UserDTO;
import com.example.jeux_frontend.model.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="gateway")
@RequestMapping("/utilisateurs")
@Component
public interface UtilisateurProxy {

    @GetMapping("/users/{id}")
    UserDTO findById(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id);

    @GetMapping("/{email}")
    Utilisateur findByEmail(@PathVariable("email") String email);
}
