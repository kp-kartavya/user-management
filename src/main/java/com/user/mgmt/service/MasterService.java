package com.user.mgmt.service;

import java.util.List;
import java.util.Map;

import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.StateDto;

public interface MasterService {
	List<CountryDto> saveAllCountry(List<CountryDto> countryDto);
	Map<String, String> fetchCountries();
	List<StateDto> saveAllStates(List<StateDto> stateDto);
	Map<String, String> fetchStates();
	Map<String, String> getStateByCountry(Long countryFk);
}
