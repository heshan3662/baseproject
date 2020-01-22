package com.springcloud.servicezuul.configuration.helper.service;


import java.io.Serializable;

/**
 * @author devezhao
 * @since 10/12/2018
 */
public interface CacheTemplate<V extends Serializable> {

    String get(String key);

    void put(String key, String value);

    void put(String key, String value, int seconds);

    V getx(String key);

    void putx(String key, V value);

    void putx(String key, V value, int seconds);

    void evict(String key);

    String getKeyPrefix();
}