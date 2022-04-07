package com.example.demo.proxy;

import com.example.demo.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

//TODO put good port + vérif method call+params+token
@FeignClient(name="gateway")
@RequestMapping("/utilisateurs")
@Component
public interface UtilisateurProxy {

    @GetMapping("/users/{id}")
    UserDTO findById(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id);

}
