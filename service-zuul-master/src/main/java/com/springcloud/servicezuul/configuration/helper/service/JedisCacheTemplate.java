package com.springcloud.servicezuul.configuration.helper.service;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * redis
 *
 * @author devezhao
 * @since 01/02/2019
 */

import redis.clients.jedis.JedisPool;

import java.io.Serializable;

public class JedisCacheTemplate<V extends Serializable> implements  CacheTemplate<V> {

    private JedisPool jedisPool;
    private String keyPrefix;

    protected JedisCacheTemplate(JedisPool jedisPool, String keyPrefix) {
        this.jedisPool = jedisPool;
        this.keyPrefix = keyPrefix == null ? "" : keyPrefix;
    }

    @Override
    public String get(String key) {
        String ckey = unityKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(ckey);
        } finally {
            IOUtils.closeQuietly(jedis);
        }
    }

    @Override
    public void put(String key, String value) {
        put(key, value, -1);
    }

    @Override
    public void put(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(unityKey(key), value);
            if (seconds > 0) {
                jedis.expire(value, seconds);
            }
        } finally {
            IOUtils.closeQuietly(jedis);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getx(String key) {
        String ckey = unityKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] bs = jedis.get(ckey.getBytes());
            if (bs == null || bs.length == 0) {
                return null;
            }

            Object s = SerializationUtils.deserialize(bs);
            // Check type of generic?
            return (V) s;
        } finally {
            IOUtils.closeQuietly(jedis);
        }
    }

    @Override
    public void putx(String key, V value) {
        putx(key, value, -1);
    }

    @Override
    public void putx(String key, V value, int seconds) {
        String ckey = unityKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] bKey = ckey.getBytes();
            jedis.set(bKey, SerializationUtils.serialize(value));
            if (seconds > 0) {
                jedis.expire(bKey, seconds);
            }
        } finally {
            IOUtils.closeQuietly(jedis);
        }
    }

    @Override
    public void evict(String key) {
        String ckey = unityKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(ckey);
        } finally {
            IOUtils.closeQuietly(jedis);
        }
    }

    @Override
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * @param key
     * @return
     */
    protected String unityKey(String key) {
        Assert.notNull(key, "[key] not be null");
        return (getKeyPrefix() + key).toUpperCase();
    }
}
