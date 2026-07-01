package ShcedulerTests;

import org.example.Journalapplication;
import org.example.Scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Journalapplication.class)
public class UserSchedulerTests {
    @Autowired
    private UserScheduler userScheduler;
    @Test
    public void testUserScheduler(){
        userScheduler.fetchUsersandsendEmails();
    }
}
