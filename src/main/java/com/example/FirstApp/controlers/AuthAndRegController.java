package com.example.FirstApp.controlers;

import com.example.FirstApp.pojo.JwtRequest;
import com.example.FirstApp.pojo.RegistrationUser;
import com.example.FirstApp.services.AutAndRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthAndRegController {
    @Autowired
    AutAndRegService service;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) @RequestBody JwtRequest request){
        return  service.createAuthToken(request);
    }
    @PostMapping("/reg")
    public ResponseEntity<?> addNewUser(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) @RequestBody RegistrationUser registrationUser) {
        return service.addNewUser(registrationUser);
    }
}
