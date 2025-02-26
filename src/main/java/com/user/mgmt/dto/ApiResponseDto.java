package com.user.mgmt.dto;

import java.util.Collections;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponseDto {
	private int status;
	private String message;
	private Map<String, String> errors;
	private Map<String, Object> response;

	public static ApiResponseDto success(int status, String message, Map<String, Object> response) {
		return ApiResponseDto.builder().status(status).message(message).errors(Collections.emptyMap())
				.response(response).build();
	}

	public static ApiResponseDto error(int status, String message, Map<String, String> errors) {
		return ApiResponseDto.builder().status(status).message(message).errors(errors).response(Collections.emptyMap())
				.build();
	}
}
