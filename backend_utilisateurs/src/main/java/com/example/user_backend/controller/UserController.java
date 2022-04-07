package com.example.user_backend.controller;

import com.example.user_backend.model.User;
import com.example.user_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/utilisateurs")
public class UserController {

    private UserService service;

    public UserController(UserService userserv) {
        this.service = userserv;
    }

    @GetMapping
    public Iterable<User> listAllUsers(@RequestHeader(name = "Authorization", required =false) String token){
        return service.findAll();
    }

    @GetMapping("/{email}")
    public User listUsersByEmail(@PathVariable("email") String email){
        return service.findByEmail(email);
    }


    @GetMapping("/users/{id}")
    public User listUsersById(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id){
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user){
        System.out.println(user);
        User u = service.saveUser(user);
        if(u == null) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(u.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public User editUser(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id, @RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@RequestHeader(name = "Authorization", required =true) String token, @PathVariable("id") int id) {
        return service.deleteUserById(id);
    }

    @PostMapping("/login")
    public boolean checkUser(@RequestBody User user){
        return service.checkUser(user);
    }

}
