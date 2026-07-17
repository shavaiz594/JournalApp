package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="JOURNAL APIs")
public class Journalcontroller {
    @Autowired
    private journalservice Journalservice;
    @Autowired
    private Userservice  userservice;
    private Map<Long,Journalentry> journalentries=new HashMap<>();
    @GetMapping
    @Operation(summary = "Returns all journal entries related to the user")
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
    @GetMapping("id/{objectid}")
    @Operation(summary = "Returns a single journal entry based on given object id")
    public ResponseEntity<?> getById(@PathVariable String objectid){
        ObjectId id=new ObjectId(objectid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntry user=userservice.findByUsername(username);
        List<Journalentry>collect=user.getJournalentries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journalentry>journalentry= Journalservice.getjournalentry(id);
            if(journalentry.isPresent()){
                return  new ResponseEntity<>(journalentry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create")
    @Operation(summary="Creates a Journal Entry for the User")
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
    @PutMapping("id/{objectid}")
    @Operation(summary = "Updates the Journal entry for the user")
    public ResponseEntity<?> UpdateJournal(@PathVariable String objectid,@RequestBody Journalentry myentry){
        ObjectId myid=new ObjectId(objectid);
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
    @DeleteMapping("id/{objectid}")
    @Operation(summary = "Deletes the Journal entry")
    public ResponseEntity<?> DeleteJournal(@PathVariable String objectid){
        ObjectId myid=new ObjectId(objectid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Journalservice.Deletejournalentry(myid,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
