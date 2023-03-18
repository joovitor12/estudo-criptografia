package com.authjwt.estudo.repositories;

import com.authjwt.estudo.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
