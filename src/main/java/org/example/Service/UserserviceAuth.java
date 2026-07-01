package org.example.Service;

import org.example.entity.UserEntry;
import org.example.repository.Userrepo;
import org.example.repository.journalrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserserviceAuth implements UserDetailsService {
    @Autowired
    private Userrepo userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry user=userrepo.findByUsername(username);
        if(user!=null){
            UserDetails userdetails=org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userdetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
