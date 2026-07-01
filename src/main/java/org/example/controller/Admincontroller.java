package org.example.controller;

import org.example.Cache.AppCache;
import org.example.Service.Userservice;
import org.example.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class Admincontroller {
    @Autowired
    private Userservice userservice;
    @Autowired
    private AppCache appcache;
    @GetMapping("/allusers")
    public ResponseEntity<?> getallusers(){
        List<UserEntry> all=userservice.Getall();
        if(all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create")
    public void createAdminuser(@RequestBody UserEntry userEntry){
        userservice.Saveadmin(userEntry);
    }
    @GetMapping("/clear-cache")
    public void clearcache(){
        appcache.init();
    }

}
