package com.example.FirstApp.controlers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static java.lang.System.out;

@RestController
public class MainController {
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured data";
    }
    
    @GetMapping("/secured")

    public String securedData() {
        return "Secured data";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminData() {
        return "Admin data";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/info")
    public StringBuilder userData(Principal principal) {
        StringBuilder answer = new StringBuilder();
        answer.append("Имя пользователя: ").append(principal.getName());
        return answer;
    }
}