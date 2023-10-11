package com.example.FirstApp.services;

import com.example.FirstApp.models.User;
import com.example.FirstApp.pojo.RegistrationUser;
import com.example.FirstApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRepository repository;
    public User createNewUser(RegistrationUser registrationUser) {
        User user = new User();
        user.setUsername(registrationUser.getUsername());
        user.setEmail(registrationUser.getEmail());
        user.setFirstname(registrationUser.getFirstname());
        user.setLastname(registrationUser.getLastname());
        user.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return repository.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
