package com.church.guest.web;

import com.church.guest.web.dto.TokenResponse;
import com.church.guest.web.dto.LoginRequest;
import com.church.guest.entity.User;
import com.church.guest.mapper.TokenMapper;
import com.church.guest.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Autowired
    public AuthenticationController( AuthenticationManager authenticationManager, TokenService tokenService ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping( value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse login(@Valid @RequestBody LoginRequest request ){

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getLogin(),request.getPassword());
        User user = (User) authenticationManager.authenticate(token).getPrincipal();

        return TokenMapper.toTokenResponse(tokenService.createToken(user));
    }

}
