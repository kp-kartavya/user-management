package com.user.mgmt.service;

import java.util.List;

import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.StateDto;
import com.user.mgmt.entity.State;

public interface LocationService {
//	public List<CountryDto> getAllCountries();
//
//	public List<StateDto> getAllStates();
	
	public List<CountryDto> getAllCountriesEhcache();
}
