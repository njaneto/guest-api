package com.church.guest.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.church.guest.entity.User;
import com.church.guest.exceptions.GuestRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value( "${token.secret}" )
    private String secret;

    public String createToken( User user ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256( secret );
            return JWT.create()
                    .withIssuer( "Guest API" )
                    .withSubject( user.getLogin() )
                    .withClaim( "roles", user.getRoles() )
                    .withExpiresAt( LocalDateTime.now().plusHours( 5 ).toInstant( ZoneOffset.of( "-03:00" ) ) )
                    .sign( algorithm );
        } catch( JWTCreationException exception ) {
            throw new GuestRuntimeException( "Error to create token jwt: " + exception, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public String getSubject( String tokenJWT ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256( secret );
            return JWT.require( algorithm )
                    .withIssuer( "Guest API" )
                    .build()
                    .verify( tokenJWT )
                    .getSubject();
        } catch( JWTVerificationException exception ) {
            throw new GuestRuntimeException( "Token JWT invalid!", HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
