package com.io.health.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.io.health.entity.Person;

public class UserDetailsImpl implements UserDetails {
    private final Optional<Person> person;

    public UserDetailsImpl(Optional<Person> person) {
        this.person = person;
    } 
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return person.orElse(new Person()).getPassword();
    }

    @Override
    public String getUsername() {
        return person.orElse(new Person()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Optional<Person> getPerson() {
        return person;
    }
}
