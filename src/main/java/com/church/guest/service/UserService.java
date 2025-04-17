package com.church.guest.service;

import com.church.guest.web.dto.UserRequest;
import com.church.guest.domain.User;
import com.church.guest.mapper.UserMapper;
import com.church.guest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService( UserRepository userRepository, PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User userCreate(UserRequest request) {

        if (Optional.ofNullable(userRepository.findByLogin(request.getLogin())).isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
}
