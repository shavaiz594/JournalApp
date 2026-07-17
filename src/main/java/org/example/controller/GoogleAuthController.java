package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.Filter.Jwtfilter;
import org.example.Service.UserserviceAuth;
import org.example.Utils.JWTUtil;
import org.example.entity.UserEntry;
import org.example.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/OAuth/google")
@Slf4j
public class GoogleAuthController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Autowired
    private UserserviceAuth userserviceAuth;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Userrepo userrepo;
    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping("/callback")
    public ResponseEntity<?> HandleGooglecallback(@RequestParam String code){
        try{
            String tokenendpoint="https://oauth2.googleapis.com/token";
            MultiValueMap<String,String>params=new LinkedMultiValueMap<>();
            params.add("client_id",clientId);
            params.add("client_secret",clientSecret);
            params.add("grant_type","authorization_code");
            params.add("code",code);
            params.add("redirect_uri","https://developers.google.com/oauthplayground");
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String,String>> request=new HttpEntity<>(params,headers);
            ResponseEntity<Map>tokenresponse=restTemplate.postForEntity(tokenendpoint,request,Map.class);
            String token=tokenresponse.getBody().get("id_token").toString();
            String userinfoURL="https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
            ResponseEntity<Map>userinforesponse=restTemplate.getForEntity(userinfoURL,Map.class);
            if(userinforesponse.getStatusCode()== HttpStatus.OK){
                String email=userinforesponse.getBody().get("email").toString();
                String name=userinforesponse.getBody().get("name").toString();
                try{
                    UserEntry user=userrepo.findByEmail(email);
                }catch (Exception e){
                    UserEntry user=new UserEntry();
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRoles(Arrays.asList("USER"));
                    user.setUsername(name);
                    userrepo.save(user);
                }
                String jwtToken=jwtUtil.generateToken(email);
                return ResponseEntity.ok(Collections.singletonMap("token",jwtToken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Error Occured while handling google callback",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
