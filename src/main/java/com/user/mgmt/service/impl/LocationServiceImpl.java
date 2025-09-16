package com.user.mgmt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final CacheSingleton cache = CacheSingleton.getInstance();
	@Autowired
	private ModelMapper modelMapper;

	public List<CountryDto> getAllCountries() {
		String key = "countries";
		if (cache.containsKey(key)) {
			log.info("Returning countries from cache");
			return (List<CountryDto>) cache.get(key);
		}

		log.info("Fetching countries from DB");
		List<Country> countries = countryRepo.findAll();
		List<CountryDto> countryDto = countries.stream().map(country -> modelMapper.map(country, CountryDto.class))
				.collect(Collectors.toList());
		cache.put(key, countryDto);
		return countryDto;
	}

	public List<StateDto> getAllStates() {
		String key = "states";
		if (cache.containsKey(key)) {
			log.info("Returning states from cache");
			return (List<StateDto>) cache.get(key);
		}

		log.info("Fetching states from DB");
		List<State> states = stateRepo.findAll();
		List<StateDto> stateDto = states.stream().map(country -> modelMapper.map(country, StateDto.class))
				.collect(Collectors.toList());
		cache.put(key, stateDto);
		return stateDto;
	}

}
