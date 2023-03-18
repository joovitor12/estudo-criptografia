package com.authjwt.estudo.services;

import com.authjwt.estudo.data.UserDetailsData;
import com.authjwt.estudo.entities.User;
import com.authjwt.estudo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceIMPL implements UserDetailsService {


    private final UserRepository repository;

    public UserDetailsServiceIMPL(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User " + id + " not found.");
        }
        return new UserDetailsData(user);
    }
}
