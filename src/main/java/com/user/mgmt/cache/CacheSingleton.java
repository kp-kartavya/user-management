package com.user.mgmt.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheSingleton {
	private static volatile CacheSingleton instance;

	private Map<String, Object> cache;

	public CacheSingleton() {
		cache = new ConcurrentHashMap<>();
	}

	public static synchronized CacheSingleton getInstance() {
		if (instance == null) {
			instance = new CacheSingleton();
		}
		return instance;
	}

	public void put(String Key, Object value) {
		cache.put(Key, value);
	}

	public Object get(String key) {
		return cache.get(key);
	}

	public boolean containsKey(String key) {
		return cache.containsKey(key);
	}

	public void remove(String key) {
		cache.remove(key);
	}

	public int size() {
		return cache.size();
	}

	public void clear() {
		cache.clear();
	}
}
