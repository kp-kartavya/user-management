package com.user.mgmt.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.RoleDto;
import com.user.mgmt.dto.StateDto;
import com.user.mgmt.entity.Country;
import com.user.mgmt.entity.Role;
import com.user.mgmt.entity.State;
import com.user.mgmt.exception.ResourceNotFoundException;
import com.user.mgmt.repo.CountryRepo;
import com.user.mgmt.repo.RoleRepo;
import com.user.mgmt.repo.StateRepo;
import com.user.mgmt.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {
	private static final Logger log = LoggerFactory.getLogger(MasterServiceImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public List<CountryDto> saveAllCountry(List<CountryDto> countryDto) {
		log.info("Inside saveAllCountry method");
		List<Country> country = countryDto.stream().map(dto -> modelMapper.map(dto, Country.class))
				.filter(c -> !countryRepo.existsByCountryName(c.getCountryName())).collect(Collectors.toList());
		for (int i = 0; i < country.size(); i++) {
			log.info("Country :: " + country.get(i).getCountryName());
		}
		return countryRepo.saveAll(country).stream().map(c -> modelMapper.map(c, CountryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, String> fetchCountries() {
		log.info("Inside fetchCountries method");
		List<Country> c = countryRepo.findAll();
		return c.stream()
				.collect(Collectors.toMap(country -> String.valueOf(country.getCountryPk()), Country::getCountryName));
	}

	@Override
	public String getCountryById(Long countryPk) {
		Country country = countryRepo.findById(countryPk)
				.orElseThrow(() -> new ResourceNotFoundException("State", "ID", countryPk.toString()));
		return country.getCountryName();
	}

	// STATE SERVICE IMPL
	@Override
	public List<StateDto> saveAllStates(List<StateDto> stateDto) {
		log.info("Inside saveAllStates method");
		List<State> state = stateDto.stream().map(dto -> modelMapper.map(dto, State.class))
				.filter(s -> !stateRepo.existsByStateName(s.getStateName())).collect(Collectors.toList());
		return stateRepo.saveAll(state).stream().map(s -> modelMapper.map(s, StateDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, String> fetchStates() {
		log.info("Inside fetchStates method");
		List<State> s = stateRepo.findAll();
		return s.stream().collect(Collectors.toMap(state -> String.valueOf(state.getStatePk()), State::getStateName));
	}

	@Override
	public Map<String, String> getStateByCountry(Long countryFk) {
		log.info("Inside getStateByCountry method");
		Country country = countryRepo.findById(countryFk)
				.orElseThrow(() -> new ResourceNotFoundException("Country", "Country_PK", countryFk.toString()));
		List<State> state = stateRepo.findByCountryFk(country.getCountryPk()).orElseThrow(
				() -> new ResourceNotFoundException("State", "Country_FK", country.getCountryPk().toString()));
		return state.stream().collect(Collectors.toMap(s -> String.valueOf(s.getStatePk()), State::getStateName));
	}

	@Override
	public String getStateById(Long statePk) {
		State state = stateRepo.findById(statePk)
				.orElseThrow(() -> new ResourceNotFoundException("State", "ID", statePk.toString()));
		return state.getStateName();
	}

	// ROLE SERVICE IMPL
	@Override
	public List<RoleDto> saveRoles(List<RoleDto> roleDto) {
		List<Role> role = roleDto.stream().map(dto -> modelMapper.map(dto, Role.class))
				.filter(r -> !roleRepo.existsByRoleName(r.getRoleName())).collect(Collectors.toList());
		return roleRepo.saveAll(role).stream().map(s -> modelMapper.map(s, RoleDto.class)).collect(Collectors.toList());
	}

	@Override
	public Map<String, String> fetchRoles() {
		List<Role> role = roleRepo.findAll();
		return role.stream().collect(Collectors.toMap(r -> String.valueOf(r.getRolePk()), Role::getRoleName));
	}

}
