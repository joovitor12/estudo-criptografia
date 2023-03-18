package com.authjwt.estudo.controllers;

import com.authjwt.estudo.entities.User;
import com.authjwt.estudo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(value = "/find-all")
    public List<User> findAll(){
        return service.findAll();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody User user){
        String result = service.save(user);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/validate-password")
    public ResponseEntity<Boolean> validatePassowrd(@RequestParam String id, @RequestParam String password){
        boolean result = service.validatePassword(id, password);
        return  ResponseEntity.ok().body(result);
    }


}
