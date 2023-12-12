package com.example.FirstApp.services;

import com.example.FirstApp.exeptions.AppError;
import com.example.FirstApp.models.User;
import com.example.FirstApp.pojo.jwt.JwtRequest;
import com.example.FirstApp.pojo.jwt.JwtResponse;
import com.example.FirstApp.pojo.user.RegistrationUser;
import com.example.FirstApp.pojo.user.UserDto;
import com.example.FirstApp.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
public class AutAndRegService {

    private static final Logger logger = LoggerFactory.getLogger(AutAndRegService.class);

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
            logger.warn("authorization error");
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"data entered incorrectly")
                    ,HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        logger.info("Inside scheduleTask - Sending logs to Kafka at ");

        logger.warn("authorization ok");
        return ResponseEntity.ok(new JwtResponse(token));
    }
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationUser registrationUser){
        if(userService.findByUsername(registrationUser.getUsername()).isPresent()){
            logger.warn("user with that name already exists");
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value(),
                    "a user with that name already exists"),HttpStatus.BAD_REQUEST);
        }
        if(!registrationUser.getPassword().equals(registrationUser.getConfirm_password())){
            logger.warn("passwords don't match");
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value(),
                    "passwords don't match"
            ),HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUser);
        logger.info("user registration - ok");
        return  ResponseEntity.ok(new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname()));
    }
}
