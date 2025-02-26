package com.user.mgmt.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InitiatorTempDto {
	private Long tempPk;
	private String requestType;
	private LocalDateTime createdDate;
	private String status;
	private String remarks;
	private UserDto userDto;
}
