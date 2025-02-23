package com.user.mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {
	private int status;
	private String message;
	private T response;

	public static <T> ApiResponseDto<T> success(int status, String message, T response) {
		return new ApiResponseDto<T>(status, message, response);
	}

	public static <T> ApiResponseDto<T> error(int status, String message, T response) {
		return new ApiResponseDto<T>(status, message, response);
	}
}
