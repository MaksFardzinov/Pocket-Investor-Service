package com.example.FirstApp.services;

import com.example.FirstApp.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final int user_id;
    private final String user_name;
    private final String user_firstname;
    private final String user_lastname;
    private final String user_email;
    @JsonIgnore
    private final String user_password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetails(int id, String user_name, String user_firstname,
                       String user_lastname,String user_email, String user_password,
                       Collection<? extends GrantedAuthority> authorities) {
        this.user_id = id;
        this.user_name = user_name;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.authorities = authorities;
    }
    public static  UserDetails build(User user) {
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetails(
                user.getUser_id(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                authorityList);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user_password;
    }

    @Override
    public String getUsername() {
        return user_name;
    }
    public int getUserId(){
        return user_id;
    }
    public String getUserEmail(){
        return user_email;
    }
    public String getUserFirstname(){
        return user_firstname;
    }
    public String getUserLastname(){
        return user_lastname;
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
}
