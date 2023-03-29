package com.io.health.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.io.health.security.JWTAuthenticationFilter;
import com.io.health.security.JWTAuthorizationFilter;
import com.io.health.security.util.JWTUtil;
import com.io.health.service.UserDetailServiceImpl;

@Configuration
public class WebSecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailServiceImpl userDetailServiceImpl)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailServiceImpl)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailServiceImpl userDetailServiceImpl,
            JWTUtil jwtUtil) throws Exception {
        http.csrf().disable().authorizeHttpRequests(authz -> {
            try {
                authz.anyRequest().authenticated().and()
                        .addFilter(new JWTAuthenticationFilter(
                                authenticationManager(http, userDetailServiceImpl), jwtUtil))
                        .addFilter(new JWTAuthorizationFilter(
                                authenticationManager(http, userDetailServiceImpl), jwtUtil));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(false).ignoring()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**")
                .requestMatchers(HttpMethod.POST, "/patient");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
