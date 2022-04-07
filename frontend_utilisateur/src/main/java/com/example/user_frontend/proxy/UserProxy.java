package com.example.user_frontend.proxy;

import com.example.user_frontend.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(name="gateway")
public interface UserProxy {

    @GetMapping("/utilisateurs")
    Iterable<User> findAll();

    @GetMapping("/utilisateurs/{email}")
    User findByEmail(@PathVariable("email") String email);

    @GetMapping("/utilisateurs/users/{id}")
    User findById(@PathVariable("id") int id);

    @PostMapping("/utilisateurs")
    void createUser(@RequestBody User user);

    @PutMapping("/utilisateurs/{id}")
    void updateUser(@PathVariable("id") int id,@RequestBody User user);

    @DeleteMapping("/utilisateurs/{id}")
    void deleteUser(@PathVariable("id") int id);

    @PostMapping("/utilisateurs/login")
    boolean checkUser(@RequestBody User user);

}
