package com.user.mgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.dto.ApiResponseDto;
import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.RoleDto;
import com.user.mgmt.dto.StateDto;
import com.user.mgmt.service.LocationService;
import com.user.mgmt.service.MasterService;
import com.user.mgmt.service.utils.Constants;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/master/")
public class MasterController {
	private static final Logger log = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	private MasterService masterService;
	@Autowired
	private LocationService locationService;

	// COUNTRY CONTROLLER
	@PostMapping("country/save")
	public ResponseEntity<ApiResponseDto> saveAllCountries(@RequestBody List<CountryDto> countryDto) {
		log.info("Inside saveAllCountries controller !!");
		List<CountryDto> country = masterService.saveAllCountry(countryDto);
		return new ResponseEntity<>(ApiResponseDto.success(Constants.createdStatus, "success",
				"Countries Saved Successfully !!", Map.of("countryList", (Object) country)), HttpStatus.CREATED);
	}

	// FETCHING COUTNRY FROM CACHE
//	@GetMapping("country/fetch")
//	public ResponseEntity<ApiResponseDto> fetchCountry() {
//		log.info("Inside fetchCountry controller !!");
//		List<CountryDto> countryList = locationService.getAllCountries();
//		Map<String, String> map = countryList.stream()
//				.collect(Collectors.toMap(country -> String.valueOf(country.getCountryPk()), CountryDto::getCountryName,
//						(existing, replacement) -> existing));
//		Map<String, Object> data = new HashMap<>();
//		data.put("countryList", map);
//
//		return new ResponseEntity<>(
//				ApiResponseDto.success(Constants.okStatus, "success", "Countries Fetched Successfully !!", data),
//				HttpStatus.OK);
//	}

	// STATE CONTROLLER
	@PostMapping("state/save")
	public ResponseEntity<ApiResponseDto> saveAllStates(@RequestBody List<StateDto> countryDto) {
		log.info("Inside saveAllStates controller !!");
		List<StateDto> state = masterService.saveAllStates(countryDto);
		return new ResponseEntity<>(ApiResponseDto.success(Constants.createdStatus, "success",
				"States Saved Successfully !!", Map.of("roleList", (Object) state)), HttpStatus.CREATED);
	}

	// FETCHING STATE FROM CACHE
//	@GetMapping("state/fetch")
//	public ResponseEntity<ApiResponseDto> fetchState() {
//		log.info("Inside fetchState controller !!");
//		List<StateDto> stateList = locationService.getAllStates();
//		Map<String, String> map = stateList.stream()
//				.collect(Collectors.toMap(state -> String.valueOf(state.getStatePk()), StateDto::getStateName,
//						(existing, replacement) -> existing));
//		Map<String, Object> data = new HashMap<>();
//		data.put("stateList", map);
//
//		return new ResponseEntity<>(ApiResponseDto.success(Constants.okStatus, "success",
//				"States Fetched Successfully !!", Map.of("stateList", (Object) map)), HttpStatus.OK);
//	}

	@GetMapping("state/fetch/{countryFk}")
	public ResponseEntity<ApiResponseDto> fetchByCountryFk(@PathVariable Long countryFk) {
		log.info("Inside fetchByCountryFk controller !!");
		Map<String, String> state = masterService.getStateByCountry(countryFk);
		Map<String, Object> data = new HashMap<>();
		data.put("stateList", state);
		return new ResponseEntity<>(ApiResponseDto.success(Constants.okStatus, "success", "State Found !!", data),
				HttpStatus.OK);
	}

	@PostMapping("role/save")
	public ResponseEntity<ApiResponseDto> saveRoles(@RequestBody List<RoleDto> roleDto) {
		log.info("Inside saveRoles controller !!");
		List<RoleDto> roles = masterService.saveRoles(roleDto);
		return new ResponseEntity<>(ApiResponseDto.success(Constants.createdStatus, "success",
				"Roles Saved Successfully !!", Map.of("roleList", (Object) roles)), HttpStatus.CREATED);
	}

	@GetMapping("role/fetch")
	public ResponseEntity<ApiResponseDto> fetchRoles() {
		log.info("Inside fetchState controller !!");
		Map<String, String> roles = masterService.fetchStates();

		return new ResponseEntity<>(ApiResponseDto.success(Constants.okStatus, "success",
				"Roles Fetched Successfully !!", Map.of("roleList", (Object) roles)), HttpStatus.OK);
	}
	
	@GetMapping("country/fetchEhcache")
	public ResponseEntity<ApiResponseDto> fetchCountryEhcache() {
		log.info("Inside fetchCountry controller !!");
		List<CountryDto> countryList = locationService.getAllCountriesEhcache();
		Map<String, String> map = countryList.stream()
				.collect(Collectors.toMap(country -> String.valueOf(country.getCountryPk()), CountryDto::getCountryName,
						(existing, replacement) -> existing));
		Map<String, Object> data = new HashMap<>();
		data.put("countryList", map);

		return new ResponseEntity<>(
				ApiResponseDto.success(Constants.okStatus, "success", "Countries Fetched Successfully !!", data),
				HttpStatus.OK);
	}
}
