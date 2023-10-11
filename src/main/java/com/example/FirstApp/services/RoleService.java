package com.example.FirstApp.services;

import com.example.FirstApp.models.ERole;
import com.example.FirstApp.models.Role;
import com.example.FirstApp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository repository;
    public Role getUserRole(){
        return repository.findByName(ERole.ROLE_USER).get();
    }
}
