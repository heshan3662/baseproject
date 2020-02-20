package com.springcloud.servicezuul.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.springcloud.servicezuul.responsetype.DataRes;
import com.springcloud.servicezuul.responsetype.ResultCode;
import com.springcloud.servicezuul.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CacheService {

    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据username获取个人信息
      * @param path
     * @param username
     * @return
     */
     public DataRes GetCacheForUserInfoByUsername( String path, String username ){
        DataRes dataRes ;
        if(StringUtils.isBlank(username)){
          dataRes = new DataRes(path, ResultCode.RESULT_ERROR, "用户名为空");
          return  dataRes ;
        }
        Object  obj = redisUtils.get("CLOUD_USER_TOKEN[" + username + "]");
        if(obj == null ){
            return null;
        }
        String  token = obj.toString();
        dataRes = GetCacheForUserInfoByToken (path,token );
        if(dataRes.get_result_code()==ResultCode.RESULT_ERROR ){
         redisUtils.delete("CLOUD_USER_TOKEN[" + username + "]");
         return null;
        }
        return  dataRes ;
     }

    /**
     * 根据token获取人员信息
     * @param path
     * @param token
     * @return
     */
    public DataRes GetCacheForUserInfoByToken( String path, String token ){
        DataRes dataRes ;
        if(StringUtils.isBlank(token)){
            dataRes = new DataRes(path, ResultCode.RESULT_ERROR, "token为空");
        }else{
            Object  obj =redisUtils.get("CLOUD_USER_INFO[" + token + "]");
            if(obj == null ){
                redisUtils.delete("CLOUD_USER_INFO[" + token + "]");
                dataRes = new DataRes(path, ResultCode.RESULE_NOLOGIN, "请重新登录");
                return  dataRes;
            }
            String data  = obj.toString();
            if(StringUtils.isBlank(data)){
                redisUtils.delete("CLOUD_USER_INFO[" + token + "]");
                dataRes = new DataRes(path, ResultCode.RESULE_NOLOGIN, "请重新登录");
            }else{
                Map map = JSON.parseObject(data,Map.class);
                String login_NAME = map.get("loginName").toString();
                String type = map.get("type").toString();
                Object   token_temp = redisUtils.get("CLOUD_USER_TOKEN_"+type+"[" + login_NAME + "]");
                if(token_temp.equals(token)){
                    dataRes = new DataRes(path, ResultCode.RESULT_SUCCESS, "成功");
                    dataRes.setData(JSON.parseObject(data,Map.class));
                }else {
                    redisUtils.delete("CLOUD_USER_INFO[" + token + "]");
                    dataRes = new DataRes(path, ResultCode.RESULE_NOLOGIN, "请重新登录");
                }

            }
        }
        return  dataRes ;
    }
    public Boolean SetCacheForUserInfo (  String dataRes  ){
         if(!StringUtils.isBlank(dataRes)){
            if (StringUtils.isNotBlank(dataRes)) {
                JSONObject json = JSON.parseObject(dataRes);
                JSONObject data =  json.getJSONObject("_data")  ;
                if(data.getString("username")==null || data.getString("token") ==null   ){
                    return false ;
                }
                redisUtils.set("CLOUD_USER_TOKEN[" + data.getString("username")  + "]", data.getString("token") );
                redisUtils.set("CLOUD_USER_INFO[" + data.getString("token") + "]", data.toJSONString());
            }
        }
        return  true ;
    }
}