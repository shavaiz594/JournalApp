package ServiceTests;

import org.example.Journalapplication;
import org.example.Service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Journalapplication.class)
@Disabled
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Test
    public void testEmailService() {
        emailService.sendEmail("shavaizumar288@gmail.com","Testing Java mail sender","Hi bro Kaisa Hai");
    }
}
