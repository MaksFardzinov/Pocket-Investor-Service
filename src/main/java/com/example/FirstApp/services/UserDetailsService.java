package com.example.FirstApp.services;

import com.example.FirstApp.models.User;
import com.example.FirstApp.pojo.RegistrationUser;
import com.example.FirstApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.
                findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Not found username: "+username));
        return com.example.FirstApp.services.UserDetails.build(user);
    }


}
