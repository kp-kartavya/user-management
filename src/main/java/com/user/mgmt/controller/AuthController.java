package com.user.mgmt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.dto.ApiResponseDto;
import com.user.mgmt.dto.LoginDto;
import com.user.mgmt.dto.UserDto;
import com.user.mgmt.service.LoginService;
import com.user.mgmt.service.UserService;
import com.user.mgmt.service.utils.Constants;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/auth/")
public class AuthController {
	@Autowired
	UserService userService;
	@Autowired
	LoginService loginService;
	
	@PostMapping("registerUser")
	public ResponseEntity<ApiResponseDto> createNewUser(@RequestBody UserDto userDto, BindingResult result) throws ParseException {
		UserDto user = userService.createNewUser(userDto);
		if (user == null) {
			return new ResponseEntity<>(
					ApiResponseDto.error(Constants.badStatus, "error", "User Already Exists", Map.of("", "")),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ApiResponseDto.success(Constants.createdStatus, "success", "User Registration Successfull",
				Map.of("userDetails", userDto)), HttpStatus.CREATED);
	}

	@PostMapping("signin")
	public ResponseEntity<ApiResponseDto> signin(@RequestBody LoginDto loginDto) {
		String token = loginService.signin(loginDto);
		
		return new ResponseEntity<>(ApiResponseDto.success(Constants.createdStatus, "success", "User Registration Successfull",
				Map.of("jwt", token)), HttpStatus.CREATED);
	}
	
}
