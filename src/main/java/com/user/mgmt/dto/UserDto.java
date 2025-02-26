package com.user.mgmt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
	@NotNull(message = "Username is required")
	@NotEmpty(message = "Username is required")
	private String username;
	private String firstName;
	private String lastName;
	private Long phoneNumber;
	private Long countryFk;
	private Long stateFk;
	private String address;
	private String status;
	@Email(message = "Kindly enter valid email !!", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	private String email;
	private String role;
}
