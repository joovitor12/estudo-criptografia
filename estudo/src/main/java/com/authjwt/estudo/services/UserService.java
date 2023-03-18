package com.authjwt.estudo.services;

import com.authjwt.estudo.entities.User;
import com.authjwt.estudo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
       return repository.findAll();
    }

    public String save(User user){
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        repository.save(user);
        return "Created user " + user.getId();
    }


    public User findById(String id) throws Exception {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new Exception("Not founded object"));
    }

    public Boolean validatePassword(String id, String password){
        Optional<User> userOptional = repository.findById(id);
        if(userOptional.isEmpty()){
            return false;
        } else {
           User user = userOptional.get();
           boolean valid = bCryptPasswordEncoder().matches(password, user.getPassword());

           return valid;
        }
    }
    }
