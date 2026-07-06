package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Service.Userservice;
import org.example.Service.UserserviceAuth;
import org.example.Utils.JWTUtil;
import org.example.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@Slf4j
public class Publiccontroller {
    @Autowired
    Userservice userservice;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserserviceAuth userserviceAuth;
    @Autowired
    private JWTUtil jwtutil;
    @GetMapping
    public List<UserEntry> getUsers() {
        return userservice.Getall();
    }
    @PostMapping("/signup")
    public void Signup(@RequestBody UserEntry userentry) {
        userservice.Saveentry(userentry);
    }
    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody UserEntry userentry) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userentry.getUsername(), userentry.getPassword()));
            UserDetails userDetails=userserviceAuth.loadUserByUsername(userentry.getUsername());
            String jwt= jwtutil.generateToken(userDetails.getUsername());
            System.out.println("Login Successfull");
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }catch(Exception e){
            log.error("Exception occured while signing up",e);
            return new ResponseEntity<>("error",HttpStatus.UNAUTHORIZED);
        }
    }
}
