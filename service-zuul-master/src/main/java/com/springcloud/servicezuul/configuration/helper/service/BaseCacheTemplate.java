

package com.springcloud.servicezuul.configuration.helper.service;

 import org.apache.commons.io.IOUtils;
import org.springframework.cache.CacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 * 
 * @author devezhao
 * @since 01/02/2019
 */
public abstract class BaseCacheTemplate<V extends Serializable> implements  CacheTemplate<V> {

	/**
	 * 默认缓存时间（90d）
	 */
	public static final int DEF_CACHE_DAYS = 60 * 60 * 24 * 90;

	final private  CacheTemplate<V> delegate;
	final private boolean useRedis;

	/**
	 * @param jedisPool
	 * @param backup The ehcache for backup
	 * @param keyPrefix
	 */
	protected BaseCacheTemplate(JedisPool jedisPool, CacheManager backup, String keyPrefix) {
		this.useRedis = testJedisPool(jedisPool);
		if (this.useRedis) {
			this.delegate = new  JedisCacheTemplate<>(jedisPool, keyPrefix);
		} else {
			this.delegate = new EhcacheTemplate<>(backup, keyPrefix);
		}
	}
	
	/**
	 * @param jedisPool
	 * @param backup The ehcache for backup
	 */
	protected BaseCacheTemplate(JedisPool jedisPool, CacheManager backup) {
		this(jedisPool, backup, null);
	}

	@Override
	public String get(String key) {
		return delegate.get(key);
	}

	@Override
	public void put(String key, String value) {
		put(key, value, DEF_CACHE_DAYS);
	}

	@Override
	public void put(String key, String value, int seconds) {
		delegate.put(key, value, seconds);
	}

	@Override
	public V getx(String key) {
		return delegate.getx(key);
	}

	@Override
	public void putx(String key, V value) {
		putx(key, value, DEF_CACHE_DAYS);
	}

	@Override
	public void putx(String key, V value, int seconds) {
		delegate.putx(key, value, seconds);
	}

	@Override
	public void evict(String key) {
		delegate.evict(key);
	}
	
	@Override
	public String getKeyPrefix() {
		return delegate.getKeyPrefix();
	}
	
	/**
	 * @return
	 */
	public  CacheTemplate<V> getCacheTemplate() {
		return delegate;
	}
	
	/**
	 * @return
	 */
	public boolean isUseRedis() {
		return useRedis;
	}
	
	/**
	 * @param jedisPool
	 * @return
	 */
	private boolean testJedisPool(JedisPool jedisPool) {
		try {
			Jedis jedis = jedisPool.getResource();
			IOUtils.closeQuietly(jedis);
			return true;
		} catch (Exception ex) {
//			Application.LOG.warn("Acquisition J/Redis failed : " + ex.getLocalizedMessage() + " !!! Using backup ehcache for " + getClass());
		}
		return false;
	}
}
