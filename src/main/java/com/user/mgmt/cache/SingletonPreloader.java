package com.user.mgmt.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.user.mgmt.service.LocationService;

@Component
public class SingletonPreloader implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(SingletonPreloader.class);

	private final LocationService locationService;

	public SingletonPreloader(LocationService locationService) {
		this.locationService = locationService;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Preloading cache...");
//		locationService.getAllCountries();
//		locationService.getAllStates();
		log.info("Cache preloaded successfully!");
	}

}
