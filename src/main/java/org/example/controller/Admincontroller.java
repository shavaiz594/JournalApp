package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="ADMIN APIs")
public class Admincontroller {
    @Autowired
    private Userservice userservice;
    @Autowired
    private AppCache appcache;
    @GetMapping("/allusers")
    @Operation(summary="Returns info of all users")
    public ResponseEntity<?> getallusers(){
        List<UserEntry> all=userservice.Getall();
        if(all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create")
    @Operation(summary="Admin is created by the current admin")
    public void createAdminuser(@RequestBody UserEntry userEntry){
        userservice.Saveadmin(userEntry);
    }
    @GetMapping("/clear-cache")
    public void clearcache(){
        appcache.init();
    }

}
