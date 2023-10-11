package com.example.FirstApp.repository;

import com.example.FirstApp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User , Integer>{
    Optional<User> findByUsername(String user_name);
    Boolean existsByUsername(String user_name);
    Boolean existsByEmail(String user_email);

}
