package com.church.guest.authentication.repository;

import com.church.guest.authentication.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByLogin(String login);
}
