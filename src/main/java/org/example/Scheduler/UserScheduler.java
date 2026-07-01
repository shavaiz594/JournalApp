package org.example.Scheduler;

import org.example.Cache.AppCache;
import org.example.Enums.Sentiment;
import org.example.Service.EmailService;
import org.example.entity.Journalentry;
import org.example.entity.UserEntry;
import org.example.repository.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepoImpl userRepoImpl;
    @Autowired
    private AppCache appCache;
//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersandsendEmails(){
        List<UserEntry> users=userRepoImpl.GetUserForSA();
        for(UserEntry user:users){
            List<Journalentry>entries=user.getJournalentries();
            List<Sentiment>sentiments=entries.stream()
                    .filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentcount=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentcount.put(sentiment,sentimentcount.getOrDefault(sentiment,0)+1);
                }
            }
            int maxsentiment=0;
            Sentiment mostfreqsentiments=null;
            for(Map.Entry<Sentiment,Integer> entry:sentimentcount.entrySet()){
                if(entry.getValue()>maxsentiment){
                    maxsentiment=entry.getValue();
                    mostfreqsentiments=entry.getKey();
                }
            }
            if(mostfreqsentiments!=null){
                emailService.sendEmail(user.getEmail(),"Weekly Sentiment Analysis","Last Week you were feeling"+mostfreqsentiments.toString());
            }
        }
    }
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
