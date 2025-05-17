package com.church.guest.exceptions;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain )
            throws ServletException, IOException {

        String traceId = UUID.randomUUID().toString();
        TraceContext.setTraceId( traceId );

        response.setHeader( "X-Trace-Id", traceId );

        try {
            filterChain.doFilter( request, response );
        } finally {
            TraceContext.clear();
        }
    }
}
