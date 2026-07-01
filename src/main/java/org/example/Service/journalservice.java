package org.example.Service;
import org.bson.types.ObjectId;
import org.example.entity.Journalentry;
import org.example.entity.UserEntry;
import org.example.repository.journalrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class journalservice {

    @Autowired
    private journalrepo journalrepo;
    @Autowired
    private Userservice userservice;
    @Transactional
    public void Saveentry(Journalentry journalentry, String username) {
        try {
            System.out.println("Username from auth = " + username);

            UserEntry user = userservice.findByUsername(username);

            System.out.println("User from DB = " + user);
            journalentry.setDate(LocalDateTime.now());
            Journalentry saved = journalrepo.save(journalentry);
            user.getJournalentries().add(saved);
            userservice.updateentry(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public void Saveentry(Journalentry journalentry) {
        journalrepo.save(journalentry);
    }
    public List<Journalentry> Getall() {
        return journalrepo.findAll();
    }
    public Optional<Journalentry> getjournalentry(ObjectId id) {
        return journalrepo.findById(id);
    }
    public void  Deletejournalentry(ObjectId id, String username) {
        UserEntry user=userservice.findByUsername(username);
        boolean removed=user.getJournalentries().removeIf(x->x.getId().equals(id));
        if(removed) {
            userservice.updateentry(user);
            journalrepo.deleteById(id);
        }
    }
}
