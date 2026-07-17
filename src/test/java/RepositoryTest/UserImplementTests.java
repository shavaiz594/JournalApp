package RepositoryTest;

import org.example.Service.UserserviceAuth;
import org.example.entity.UserEntry;
import org.example.repository.Userrepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.*;


public class UserImplementTests {
    @InjectMocks
    private UserserviceAuth userserviceAuth;
    @Mock
    private Userrepo userrepo;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

//    public void testloaduserbyname(){
//        UserEntry user= UserEntry.builder().username("Shazi")
//                .password("12345")
//                .roles(List.of("USER")).sentimentAnalysis().build();
//        when(userrepo.findByUsername(ArgumentMatchers.anyString())).thenReturn(user);
//        Assertions.assertNotNull(user);
//        UserDetails User= userserviceAuth.loadUserByUsername("Shazi");
//    }

}
