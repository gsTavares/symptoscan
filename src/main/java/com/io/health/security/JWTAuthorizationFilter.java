package com.io.health.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.health.security.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private JWTUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().append(json(request));
            response.getWriter().flush();
            return;
        }
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
        if(authenticationToken == null) {
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().append(json(request));
            response.getWriter().flush();
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        DecodedJWT decodedToken = JWT.decode(token);
        if(decodedToken.getExpiresAt() == null) {
            return null;
        }
        if(decodedToken.getExpiresAt().before(new Date())){
            return null;
        }
        String person = JWT.require(Algorithm.HMAC512(jwtUtil.getJwtSecret()))
                .build()
                .verify(token)
                .getSubject();
        if (person == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(person, null, new ArrayList<>());
    }

    private CharSequence json(HttpServletRequest request) {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 403, "
                + "\"error\": \"Not allowed\", "
                + "\"message\": \"Resource not allowed\", "
                + "\"path\":\"" + request.getRequestURI().toString() + "\"}";
    }

}
