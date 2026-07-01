package org.example.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.Api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key, Class<T> entityclass) {
        try{
            Object o=redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(),entityclass);
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return null;
        }
    }
    public void set(String key,Object o,long ttl) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String json=objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        }catch(Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
