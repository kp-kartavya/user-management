package com.user.mgmt.controller;

import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.dto.ApiResponseDto;
import com.user.mgmt.dto.InitiatorTempDto;
import com.user.mgmt.dto.UserDto;
import com.user.mgmt.service.UserService;
import com.user.mgmt.service.utils.Constants;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/user/")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("saveRequest")
	public ResponseEntity<ApiResponseDto> saveRequest(@Valid @RequestBody UserDto userDto, BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			log.info(String.format("Errors %s", errors));
			return new ResponseEntity<>(ApiResponseDto.error(Constants.badStatus, "Failed!", errors),
					HttpStatus.BAD_REQUEST);
		}
		InitiatorTempDto map = userService.saveRequest(userDto);
		if (map != null) {
			return new ResponseEntity<>(ApiResponseDto.success(Constants.okStatus,
					"Request Created with Request Id " + map.getTempPk(), Map.of("", (Object) map)),
					HttpStatus.CREATED);
		}
		return new ResponseEntity<>(ApiResponseDto.success(Constants.dupStatus,
				"Request Already Initiated for User " + userDto.getUsername(), Map.of()), HttpStatus.CONFLICT);

	}

	@PostMapping("approveRequest/{tempFk}")
	public ResponseEntity<ApiResponseDto> approveRequest(@PathVariable Long tempFk) throws ParseException {
		InitiatorTempDto temp = userService.approveRequest(tempFk);
		return new ResponseEntity<>(ApiResponseDto.success(Constants.okStatus,
				"Request Approved with Request Id " + temp.getTempPk(), Map.of("", (Object) temp)), HttpStatus.CREATED);
	}

}
