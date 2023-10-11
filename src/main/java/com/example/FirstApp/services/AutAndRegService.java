package com.example.FirstApp.services;

import com.example.FirstApp.exeptions.AppError;
import com.example.FirstApp.models.User;
import com.example.FirstApp.pojo.JwtRequest;
import com.example.FirstApp.pojo.JwtResponse;
import com.example.FirstApp.pojo.RegistrationUser;
import com.example.FirstApp.pojo.UserDto;
import com.example.FirstApp.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class AutAndRegService {
    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),request.getPassword()));
        }
        catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"data entered incorrectly")
                    ,HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationUser registrationUser){
        if(userService.findByUsername(registrationUser.getUsername()).isPresent()){
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value(),
                    "a user with that name already exists"),HttpStatus.BAD_REQUEST);
        }
        if(!registrationUser.getPassword().equals(registrationUser.getConfirm_password())){
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value(),
                    "passwords don't match"
            ),HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUser);
        return  ResponseEntity.ok(new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname()));
    }
}
