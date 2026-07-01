package org.example.Service;

import org.example.Api.response.WeatherResponse;
import org.example.Cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Weatherservice {
    @Value("${weather.api.key}")
    private String apikey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;
    public WeatherResponse getWeather(String location){
        WeatherResponse weatherResponse=redisService.get(location,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else {
            String finalAPI = appCache.appcache.get("weatherAPI").replace("<apikey>", apikey).replace("<location>", location);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            if(response.getBody()!=null){
                redisService.set(location,response.getBody(), 300L);
            }
            return response.getBody();

        }
    }
}
