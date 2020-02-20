package com.example.login.service;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.RedisUtils;
import com.example.login.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CacheService {

    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据token获取人员信息
     * @param token
     * @return
     */
    public User GetCacheForUserInfoByToken(   String token ){
        if(StringUtils.isBlank(token)){
            return  null ;
        }else{
            Object  obj =redisUtils.get("CLOUD_USER_INFO[" + token + "]");
            if(obj==null ){
                return  null ;
            }
            User User = JSON.parseObject(obj.toString(), User.class);
            return   User ;
        }
    }

    /**
     * 根据loginname获取个人信息
      * @param loginname
     * @return
     */
     public  User GetCacheForUserInfoByUsername(String loginname  ,String type ){
          String token =   GetCacheForTokenByUsername(loginname,type);
          return  GetCacheForUserInfoByToken(token);
     }

    /**
     * 根据username 获取token
     * @param loginname
     * @return
     */
    public String  GetCacheForTokenByUsername(String loginname,String type ){
        Object  obj = redisUtils.get("CLOUD_USER_TOKEN_"+type +"[" + loginname + "]");
        if(obj == null ){
            return null;
        }else {
            return obj.toString();
        }
     }

    /**
     * 存放用户信息
     * @param  user
     * @return
     */

    public Boolean SetCacheForUserInfo (   User  user  ){
         if( user != null  ){
            deleteCacheForUserInfoByUserName (     user.getLoginName() ,user.getType() ) ;
            redisUtils.set("CLOUD_USER_TOKEN_"+user.getType() +"[" +  user.getLoginName() + "]",  user.getToken() );
            redisUtils.set("CLOUD_USER_INFO[" +  user.getToken() + "]", JSON.toJSONString(user)  );
         }
        return  true ;
    }


    /**
     * 删除用户信息
     * @param token
     * @return
     */
    public Boolean deleteCacheForUserInfo (  String  token  ){
        User user = GetCacheForUserInfoByToken(token);
        if(token != null  ){
            redisUtils.delete("CLOUD_USER_TOKEN_"+user.getType() +"[" + user.getLoginName() + "]"  );
            redisUtils.delete("CLOUD_USER_INFO[" + user.getToken()+ "]" );
        }
        return  true ;
    }

    /**
     * 删除用户信息
     * @param username
     * @return
     */
    public Boolean deleteCacheForUserInfoByUserName (  String  username ,String type ){

        String token =GetCacheForTokenByUsername(username,type);
        if(!StringUtils.isBlank(token)  ){
            redisUtils.delete("CLOUD_USER_INFO[" + token+ "]" );
            redisUtils.delete("CLOUD_USER_TOKEN_"+type +"[" + username + "]"  );
        }
        return  true ;
    }

    /**
     * 删除所有类型的用户信息
     * @param username
     * @return
     */
    public Boolean deleteCacheForUserInfoByUserName (  String  username   ){
        deleteCacheForUserInfoByUserName(username,"0");
        deleteCacheForUserInfoByUserName(username,"1");
        deleteCacheForUserInfoByUserName(username,"2");
        return  true ;
    }
}