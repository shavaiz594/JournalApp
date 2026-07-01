package RedisTests;

import org.example.Journalapplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = Journalapplication.class)
public class Redistest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    public void testConnection(){
        redisTemplate.opsForValue().set("email","gmail@email.com");
        Object email = redisTemplate.opsForValue().get("email");
        System.out.println(email);
    }
}
