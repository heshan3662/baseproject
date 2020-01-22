package com.example.login.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.login.Utils.RedisUtils;
import com.example.login.entity.GdUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CacheService {

    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据username获取个人信息
      * @param username
     * @return
     */
     public GdUser GetCacheForUserInfoByUsername(String username ){
          String token =   GetCacheForTokenByUsername(username);
          return  GetCacheForUserInfoByToken(token);
     }

    /**
     * 根据username 获取token
     * @param username
     * @return
     */
    public String  GetCacheForTokenByUsername(String username ){
        Object  obj = redisUtils.get("CLOUD_USER_TOKEN[" + username + "]");
        if(obj == null ){
            return null;
        }else {
            return obj.toString();
        }
     }
    /**
     * 根据token获取人员信息
     * @param token
     * @return
     */
    public GdUser GetCacheForUserInfoByToken(   String token ){
        if(StringUtils.isBlank(token)){
            return  null ;
        }else{
            Object  obj =redisUtils.get("CLOUD_USER_INFO[" + token + "]");
            if(obj==null ){
                return  null ;
            }
            GdUser gdUser = JSON.parseObject(obj.toString(),GdUser.class);
            return  gdUser ;
        }
    }

    /**
     * 存放用户信息
     * @param gdUser
     * @return
     */
    public Boolean SetCacheForUserInfo (  GdUser gdUser  ){
         if(gdUser != null  ){
                redisUtils.set("CLOUD_USER_TOKEN[" + gdUser.getUsername() + "]", gdUser.getToken() );
                redisUtils.set("CLOUD_USER_INFO[" + gdUser.getToken() + "]", JSON.toJSONString(gdUser)  );
         }
        return  true ;
    }


    /**
     * 存放用户信息
     * @param token
     * @return
     */
    public Boolean deleteCacheForUserInfo (  String  token  ){

        Object obj = redisUtils.get("CLOUD_USER_INFO[" + token + "]");
        if(obj==null ){
            return  true ;
        }
        GdUser gdUser = JSON.parseObject(obj.toString(),GdUser.class);
        if(token != null  ){
            redisUtils.delete("CLOUD_USER_TOKEN[" + gdUser.getUsername() + "]"  );
            redisUtils.delete("CLOUD_USER_INFO[" + gdUser.getToken() + "]" );
        }
        return  true ;
    }
}