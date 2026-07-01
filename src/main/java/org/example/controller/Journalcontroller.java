package org.example.controller;

import org.bson.types.ObjectId;
import org.example.Service.Userservice;
import org.example.Service.journalservice;
import org.example.entity.Journalentry;
import org.example.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class Journalcontroller {
    @Autowired
    private journalservice Journalservice;
    @Autowired
    private Userservice  userservice;
    private Map<Long,Journalentry> journalentries=new HashMap<>();
    @GetMapping
    public ResponseEntity<?> getALL(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user=userservice.findByUsername(username);
        List<Journalentry>all=user.getJournalentries();
        if(all!=null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myid}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user=userservice.findByUsername(username);
        List<Journalentry>collect=user.getJournalentries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journalentry>journalentry= Journalservice.getjournalentry(myid);
            if(journalentry.isPresent()){
                return  new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create")
    public ResponseEntity<?> CreateJournal(@RequestBody Journalentry myentry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Journalservice.Saveentry(myentry, username);
            return new ResponseEntity<>(myentry, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("id/{myid}")
    public ResponseEntity<?> UpdateJournal(@PathVariable ObjectId myid,@RequestBody Journalentry myentry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user=userservice.findByUsername(username);
        List<Journalentry>collect=user.getJournalentries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journalentry>journalentry= Journalservice.getjournalentry(myid);
            if(journalentry.isPresent()){
                Journalentry old=journalentry.get();
                old.setName(myentry.getName()!=null && myentry.getName().equals("")?myentry.getName():old.getName());
                old.setContent(myentry.getContent()!=null && myentry.getContent().equals("")?myentry.getContent():old.getContent());
                Journalservice.Saveentry(old);
                return  new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> DeleteJournal(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Journalservice.Deletejournalentry(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
