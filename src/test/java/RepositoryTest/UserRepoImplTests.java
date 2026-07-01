package RepositoryTest;

import org.example.Journalapplication;
import org.example.repository.UserRepoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= Journalapplication.class)
public class UserRepoImplTests {
    @Autowired
    private UserRepoImpl userRepo;
    @Test
    public void findnamebySA() {
        userRepo.GetUserForSA();
    }
}
