package org.example.Cache;

import org.example.entity.AppCacheEntity;
import org.example.repository.AppCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AppCache {
    @Autowired
    private AppCacheRepo appCacheRepo;
    public Map<String,String>appcache;
    @PostConstruct
    public void init(){
        appcache=new HashMap<>();
        List<AppCacheEntity> All=appCacheRepo.findAll();
        for(AppCacheEntity appCacheEntity:All){
            appcache.put(appCacheEntity.getKey(),appCacheEntity.getValue());
        }
    }
}
