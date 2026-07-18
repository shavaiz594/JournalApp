package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="PUBLIC APIs")
public class Publiccontroller {
    @Autowired
    Userservice userservice;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserserviceAuth userserviceAuth;
    @Autowired
    private JWTUtil jwtutil;
    @PostMapping("/signup")
    @Operation(summary = "Saves the Info of the new user")
    public void Signup(@RequestBody UserEntry userentry) {
        userservice.Saveentry(userentry);
    }
    @PostMapping("/login")
    @Operation(summary = "Verifies the user and generates a JWT token")
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
    @GetMapping("/")
    public ResponseEntity<?>healthcheck() {
        return new ResponseEntity<>("Service is running in Backend",HttpStatus.OK);
    }
}
