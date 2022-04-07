package com.example.user_backend.service;

import com.example.user_backend.model.User;
import com.example.user_backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Iterable<User> findAll(){ return repository.findAll();}

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

    public Iterable<User> findByNameAndSurname(String name, String prenom){
        return repository.findByNomAndPrenom(name, prenom);
    }

    public User findById (int id){
        return repository.findById(id);
    }

    public User saveUser(User user){
        return repository.save(user);
    }

    public User updateUser(User user){
        User userToUpdate = repository.findById(String.valueOf(user.getId())).orElseThrow(InternalError::new);
        userToUpdate.setAdresse(user.getAdresse());
        userToUpdate.setDateNaissance(user.getDateNaissance());
        userToUpdate.setEstAdministrateur(user.isEstAdministrateur());
        userToUpdate.setNom(user.getNom());
        userToUpdate.setPrenom(user.getPrenom());
        userToUpdate.setMotDePasse(user.getMotDePasse());
        userToUpdate.setPseudo(user.getPseudo());
        return repository.save(userToUpdate);
    }

    public User deleteUserById(int id){
        return repository.deleteUserById(id);
    }

    public boolean checkUser(User user) {
        User u=null;
        try{
            u=repository.findByEmail(user.getEmail());
        }catch (Exception e){
            return false;
        }
        return new BCryptPasswordEncoder().matches(user.getMotDePasse(),u.getMotDePasse());
    }
}
