package com.io.health.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.health.service.util.ApiResponse;

@RestController
@RequestMapping(value = "/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {
    
    @Autowired
    private ApiResponse<String> apiResponse;

    @GetMapping(value = "/validate-token")
    public ResponseEntity<ApiResponse<String>> validateToken(@RequestHeader(name = "Authorization", required = false) String token) {
        String content = token.substring(7);
        try {
            DecodedJWT decodedToken = JWT.decode(content);
            Boolean isTokenExpired = decodedToken.getExpiresAt().before(new Date());
            if(isTokenExpired) {
                throw new JWTDecodeException("Token expired");
            }
            return apiResponse.ok(token, "is valid!");
        } catch(JWTDecodeException e) {
            throw new JWTDecodeException("Invalid token");
        }
    }

}
