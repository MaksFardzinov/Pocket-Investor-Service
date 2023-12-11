package com.example.FirstApp.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
}
