package com.church.guest.security;

import ch.qos.logback.core.util.StringUtil;
import com.church.guest.service.AuthenticationService;
import com.church.guest.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final AuthenticationService authenticationService;

    @Autowired
    public SecurityFilter( TokenService tokenService, AuthenticationService authenticationService ) {
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }


    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {

        var tokenJWT = getTokenJWT( request );
        if( tokenJWT != null ) {
            var subject = tokenService.getSubject( tokenJWT );
            var user = authenticationService.loadUserByUsername( subject );

            var auth = new UsernamePasswordAuthenticationToken( user, null, user.getAuthorities() );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication( auth );
        }

        filterChain.doFilter( request, response );
    }

    private String getTokenJWT( HttpServletRequest request ) {

        if( !StringUtil.isNullOrEmpty( request.getHeader( "Authorization" ) ) ) {
            return request.getHeader( "Authorization" ).replace( "Bearer ", "" );
        }
        return null;
    }
}
