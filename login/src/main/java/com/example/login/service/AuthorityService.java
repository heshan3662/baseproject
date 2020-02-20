package com.example.login.service;

import com.example.login.DBinterface.DBinterface;
import com.example.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    com.example.login.DBinterface.DBinterface DBinterface;
    @Autowired
    CacheService cacheService;

    public  boolean user_authority( String token ,
                             String action_id){
        User user = cacheService.GetCacheForUserInfoByToken(token);
        return true ;
    }
}
