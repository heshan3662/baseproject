package com.example.login.service;

import com.alibaba.fastjson.JSON;
import com.example.login.DBinterface.DBinterface;
import com.example.login.Utils.SystemUtil;
import com.example.login.Utils.UUIDTool;
import com.example.login.entity.GdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
public class LoginService{
     @Autowired
     DBinterface DBinterface;
    @Autowired
    CacheService cacheService;
     public String login_in( String username,String password,String type){
         String sql = "select   *from gd_user where username = '"+ username+"'  and  userpass = '" + password + "'";
         Map map =  DBinterface.queryForObj(SystemUtil.CONFIG_DB1,sql , 0 );
         if(map == null  ){
             return null ;
         }
         if(map.size()<=0 ){
             return null ;
         }
         UUIDTool uuidTool= new UUIDTool();
         String token=uuidTool.getUUID(); //生成token
         map.put("token",token);
         GdUser gdUser = JSON.parseObject(JSON.toJSONString(map),GdUser.class);
         cacheService.SetCacheForUserInfo(gdUser);
         return JSON.toJSONString(map);
     }

    public String get_authority( String userid ){
        String sql = "SELECT auths  FROM `gd_sys_role`  where id = '"+ userid+"' "  ;
        Map map =  DBinterface.queryForObj(SystemUtil.CONFIG_DB1,sql , 0 );
        return JSON.toJSONString(map);
    }

    public boolean login_out( String token ){
        return cacheService.deleteCacheForUserInfo(token);
    }

}