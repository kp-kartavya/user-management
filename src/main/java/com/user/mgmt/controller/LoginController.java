package com.user.mgmt.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.mgmt.dto.ApiResponseDto;
import com.user.mgmt.dto.LoginDto;
import com.user.mgmt.service.LoginService;
import com.user.mgmt.service.UserService;
import com.user.mgmt.service.utils.Constants;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/v1/login/")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;

	@PostMapping("default")
	public ResponseEntity<ApiResponseDto> defaultPassword(@RequestBody LoginDto loginDto) throws ParseException {
		boolean userExists = loginService.defaultPassword(loginDto.getUsername(), loginDto.getPassword());

		if (!userExists) {
			return new ResponseEntity<>(
					ApiResponseDto.error(Constants.okStatus, "error", "User DoesNot Exists", Map.of("", "")),
					HttpStatus.CREATED);
		}
		return new ResponseEntity<>(
				ApiResponseDto.success(Constants.okStatus, "default", "User Details", Map.of("", "")),
				HttpStatus.CREATED);
	}

	@PostMapping("changePassword")
	public ResponseEntity<ApiResponseDto> changePassword(@RequestBody LoginDto loginDto) {
		if (!userService.existsByUsername(loginDto.getUsername()))
			return new ResponseEntity<>(
					ApiResponseDto.error(Constants.okStatus, "error", "User Does Not Exists", Map.of("", "")),
					HttpStatus.CREATED);
		
		loginService.changePassword(loginDto.getUsername(), loginDto.getPassword());
		
		return new ResponseEntity<>(
				ApiResponseDto.success(Constants.okStatus, "success", "Password Changed Successfully", Map.of("", "")),
				HttpStatus.CREATED);
	}

}
