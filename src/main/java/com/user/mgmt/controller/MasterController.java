package com.user.mgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.dto.ApiResponseDto;
import com.user.mgmt.dto.CountryDto;
import com.user.mgmt.dto.StateDto;
import com.user.mgmt.service.MasterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/master/")
public class MasterController {
	private static final Logger log = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	private MasterService masterService;

	// COUNTRY CONTROLLER
	@PostMapping("country/save")
	public ResponseEntity<ApiResponseDto<List<CountryDto>>> saveAllCountries(@RequestBody List<CountryDto> countryDto) {
		log.info("Inside saveAllCountries controller !!");
		List<CountryDto> country = masterService.saveAllCountry(countryDto);
		return new ResponseEntity<>(
				ApiResponseDto.success(HttpStatus.CREATED.value(), "Countries Saved Successfully !!", country),
				HttpStatus.CREATED);
	}

	@GetMapping("country/fetch")
	public ResponseEntity<ApiResponseDto<Map<String, Object>>> fetchCountry() {
		log.info("Inside fetchCountry controller !!");
		Map<String, String> map = masterService.fetchCountries();
		Map<String, Object> data = new HashMap<>();
		data.put("countryList", map);

		return new ResponseEntity<>(
				ApiResponseDto.success(HttpStatus.OK.value(), "Countries Fetched Successfully !!", data),
				HttpStatus.OK);
	}

	// STATE CONTROLLER
	@PostMapping("state/save")
	public ResponseEntity<ApiResponseDto<List<StateDto>>> saveAllStates(@RequestBody List<StateDto> countryDto) {
		log.info("Inside saveAllStates controller !!");
		List<StateDto> state = masterService.saveAllStates(countryDto);
		return new ResponseEntity<>(
				ApiResponseDto.success(HttpStatus.CREATED.value(), "States Saved Successfully !!", state),
				HttpStatus.CREATED);
	}

	@GetMapping("state/fetch")
	public ResponseEntity<ApiResponseDto<Map<String, Object>>> fetchState() {
		log.info("Inside fetchState controller !!");
		Map<String, String> map = masterService.fetchStates();
		Map<String, Object> data = new HashMap<>();
		data.put("stateList", map);

		return new ResponseEntity<>(
				ApiResponseDto.success(HttpStatus.OK.value(), "States Fetched Successfully !!", data), HttpStatus.OK);
	}

	@GetMapping("state/fetch/{countryFk}")
	public ResponseEntity<ApiResponseDto<Map<String, Object>>> fetchByCountryFk(@PathVariable Long countryFk) {
		log.info("Inside fetchByCountryFk controller !!");
		Map<String, String> state = masterService.getStateByCountry(countryFk);
		Map<String, Object> data = new HashMap<>();
		data.put("stateList", state);
		return new ResponseEntity<>(ApiResponseDto.success(HttpStatus.OK.value(), "State Found !!", data), HttpStatus.OK);
	}

}
