package com.user.mgmt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.cache.CacheManager;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.user.mgmt.cache.CacheSingleton;
import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.StateDto;
import com.user.mgmt.entity.Country;
import com.user.mgmt.entity.State;
import com.user.mgmt.repo.CountryRepo;
import com.user.mgmt.repo.StateRepo;
import com.user.mgmt.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;

	private final CacheManager cacheManager;

	public LocationServiceImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	private final CacheSingleton cache = CacheSingleton.getInstance();
	@Autowired
	private ModelMapper modelMapper;

	// SINGLETON CACHING
//	@SuppressWarnings("unchecked")
//	public List<CountryDto> getAllCountries() {
//		String key = "countries";
//		if (cache.containsKey(key)) {
//			log.info("Returning countries from cache");
//			return (List<CountryDto>) cache.get(key);
//		}
//
//		log.info("Fetching countries from DB");
//		List<Country> countries = countryRepo.findAll();
//		List<CountryDto> countryDto = countries.stream().map(country -> modelMapper.map(country, CountryDto.class))
//				.collect(Collectors.toList());
//		cache.put(key, countryDto);
//		return countryDto;
//	}

//	@SuppressWarnings("unchecked")
//	public List<StateDto> getAllStates() {
//		String key = "states";
//		if (cache.containsKey(key)) {
//			log.info("Returning states from cache");
//			return (List<StateDto>) cache.get(key);
//		}
//
//		log.info("Fetching states from DB");
//		List<State> states = stateRepo.findAll();
//		List<StateDto> stateDto = states.stream().map(country -> modelMapper.map(country, StateDto.class))
//				.collect(Collectors.toList());
//		cache.put(key, stateDto);
//		return stateDto;
//	}

	// EHCACHE
	@Cacheable(value = "countriesEhcache", key = "'all'")
	public List<CountryDto> getAllCountriesEhcache() {
		@SuppressWarnings("unchecked")
		List<CountryDto> cached = (List<CountryDto>) cacheManager
				.getCache("countriesEhcache", String.class, Object.class).get("countriesEhcache");

		if (cached != null) {
			log.info("Returning countries from cache");
			return cached;
		}

		// Fallback (shouldn’t happen if preload worked)
		List<CountryDto> countries = countryRepo.findAll().stream().map(c -> modelMapper.map(c, CountryDto.class))
				.collect(Collectors.toList());

		cacheManager.getCache("countriesEhcache", String.class, Object.class).put("countriesEhcache", countries);

		return countries;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void preloadCountries() {
		getAllCountriesEhcache(); // triggers @Cacheable → caches automatically
		log.info("✅ Countries preloaded into cache");
	}
}
