package com.example.login.Configuration.helper.service;



 import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Ehcache
 *
 * @author devezhao
 * @since 01/02/2019
 */
public class EhcacheTemplate<V extends Serializable> implements  CacheTemplate<V> {

	private CacheManager ehcacheManager;
	private String keyPrefix;

	protected EhcacheTemplate(CacheManager ehcacheManager, String keyPrefix) {
		this.ehcacheManager = ehcacheManager;
		this.keyPrefix = keyPrefix == null ? "" : keyPrefix;
	}

	@Override
	public String get(String key) {
		ValueWrapper w = cache().get(unityKey(key));
		return w == null ? null : (String) w.get();
	}

	@Override
	public void put(String key, String value) {
		put(key, value, -1);
	}

	@Override
	public void put(String key, String value, int seconds) {
		Element el = new Element(unityKey(key), value);
		if (seconds > -1) {
			el.setTimeToLive(seconds);
		}
		((Ehcache) cache().getNativeCache()).put(el);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getx(String key) {
		ValueWrapper w = cache().get(unityKey(key));
		return w == null ? null : (V) w.get();
	}

	@Override
	public void putx(String key, V value) {
		putx(key, value, -1);
	}

	@Override
	public void putx(String key, V value, int seconds) {
		Element el = new Element(unityKey(key), value);
		if (seconds > -1) {
			el.setTimeToLive(seconds);
		}
		((Ehcache) cache().getNativeCache()).put(el);
	}

	@Override
	public void evict(String key) {
		String ckey = unityKey(key);
		cache().evict(ckey);
	}

	/**
	 * @return
	 */
	public Cache cache() {
		return ehcacheManager.getCache("rebuild");
	}

	@Override
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * @param key
	 * @return
	 */
	protected String unityKey(String key) {
		Assert.notNull(key, "[key] not be null");
		return (getKeyPrefix() + key).toLowerCase();
	}

	/**
	 * 关闭 Ehcache 以便将缓存持久化到硬盘
	 */
	public void shutdown() {
//		Application.LOG.info("Ehcache shutdown ...");
		((EhCacheCacheManager) ehcacheManager).getCacheManager().shutdown();
	}
}
