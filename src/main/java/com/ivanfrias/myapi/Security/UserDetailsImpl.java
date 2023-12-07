package com.ivanfrias.myapi.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ivanfrias.myapi.Models.Users;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final Users usuario;

    public UserDetailsImpl(Users usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }

    public Long getId() {
        return usuario.getId();
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

    public String getName(){
        return usuario.getName();
    }

    public String getEmail() {
        return usuario.getEmail();
    }


    public String getLastname() {
        return usuario.getLastname();
    }

    public boolean getIsEmailVerified() {
        return usuario.isEmailVerified();
    }


}
