package org.example.controller;

import org.example.Api.response.WeatherResponse;
import org.example.Service.Userservice;
import org.example.Service.Weatherservice;
import org.example.entity.UserEntry;
import org.example.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private Userservice userservice;
    @Autowired
    private Userrepo userrepo;
    @Autowired
    private Weatherservice weatherservice;
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry userindb=userservice.findByUsername(username);
        if(userindb!=null){
            userindb.setUsername(userentry.getUsername());
            userindb.setPassword(userentry.getPassword());
            userservice.Saveentry(userindb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserEntry userentry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userrepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> Greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherresponse = weatherservice.getWeather("Hyderabad");
        String greeting="";
        if(weatherresponse!=null){
            greeting=", The weather feels like "+weatherresponse.getMain().getFeels_like();
        }
        return new ResponseEntity<>("Hi "+ authentication.getName()+ greeting, HttpStatus.OK);
    }


}
