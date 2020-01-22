package com.springcloud.servicezuul.configuration.helper.service;

import com.springcloud.servicezuul.configuration.helper.entity.Status;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitService  {

    @Value("${spring.redis.host}")
     String  host ;
    @Value("${spring.redis.port}")
     String  port ;
    @Value("${spring.isUseRedis}")
     String  isUseRedis ;




    public boolean initService(  ){
       return checkAll();
     }
    /**
     * 系统状态检查
     *
     * @return
     */
    public   boolean checkAll() {
        List<Status> last = new ArrayList<>();

//        last.add(checkCreateFile());
//        last.add(checkDatabase());
        last.add(checkCacheService());

//        synchronized (LAST_STATUS) {
//            LAST_STATUS.clear();
//            LAST_STATUS.addAll(last);
//        }
        return isStatusOK(last);
     }

    /**
     * 缓存系统
     *
     * @return
     */
    public Status checkCacheService() {

        String name = "Cache/" + (  "0".equals(isUseRedis) ? "REDIS" : "EHCACHE");
        testJedisPool(new JedisPool(host,new Integer(port)));
        return Status.success(name);
    }

    private boolean testJedisPool(JedisPool jedisPool) {
        try {
            Jedis jedis = jedisPool.getResource();
            IOUtils.closeQuietly(jedis);
            return true;
        } catch (Exception ex) {
//            Application.LOG.warn("Acquisition J/Redis failed : " + ex.getLocalizedMessage() + " !!! Using backup ehcache for " + getClass());
        }
        return false;
    }
    /**
     * 服务是否正常
     *
     * @return
     */
    public static boolean isStatusOK(List<Status> StatusList) {
        for (Status s : StatusList) {
            if (!s.success) {
                return false;
            }
        }
        return true;
    }

}