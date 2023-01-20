package com.church.guest.security;

import com.church.guest.service.AuthenticationService;
import com.church.guest.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = getTokenJWT(request);
        if(tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var user = authenticationService.loadUserByUsername(subject);

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenJWT(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if(authorization != null){
            return authorization.replace("Bearer ","");
        }
        return null;
    }
}
