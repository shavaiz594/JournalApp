package org.example.Service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.example.entity.Journalentry;
import org.example.entity.UserEntry;
import org.example.repository.Userrepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class Userservice {
    @Autowired
    private Userrepo userrepo;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void Saveentry(UserEntry userEntry) {
        try {
            userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
            userEntry.setRoles(Arrays.asList("USER"));
            userrepo.save(userEntry);
        }
        catch (Exception e) {
            log.error("error occured for {} :", userEntry.getUsername(), e);
        }
    }
    public void Saveadmin(UserEntry userEntry) {
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER","ADMIN"));
        userrepo.save(userEntry);
    }
    public void updateentry(UserEntry userEntry) {
        userrepo.save(userEntry);
    }
    public List<UserEntry> Getall() {
        return userrepo.findAll();
    }
    public Optional<UserEntry> getjournalentry(ObjectId id) {
        return userrepo.findById(id);
    }
    public UserEntry findByUsername(String username){
        return userrepo.findByUsername(username);
    }
}
