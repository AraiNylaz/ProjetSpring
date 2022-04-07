package com.example.user_backend.repository;

import com.example.user_backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
    Iterable<User> findByNomAndPrenom(String nom, String prenom);
    User findById(int id);
    //TODO
    User save(User user);
    User deleteUserById(int id);
}
