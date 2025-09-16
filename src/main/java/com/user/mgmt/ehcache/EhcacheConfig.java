package com.user.mgmt.ehcache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig {
	@Bean
	CacheManager cacheManager() {
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cm = cachingProvider.getCacheManager();

		MutableConfiguration<String, Object> countryConfig = new MutableConfiguration<String, Object>()
				.setTypes(String.class, Object.class).setStoreByValue(false)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.TEN_MINUTES));

		cm.createCache("countriesEhcache", countryConfig);
		
		MutableConfiguration<String, Object> stateConfig = new MutableConfiguration<String, Object>()
				.setTypes(String.class, Object.class).setStoreByValue(false)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.TEN_MINUTES));

		cm.createCache("states", stateConfig);

		return cm;
	}

	@Bean
	org.springframework.cache.CacheManager cacheManagerSpring(CacheManager ehCacheManager) {
		return new JCacheCacheManager(ehCacheManager);
	}
}
