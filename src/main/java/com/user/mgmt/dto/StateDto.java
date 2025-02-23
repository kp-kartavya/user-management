package com.user.mgmt.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDto {
	private Long statePk;
	private String stateName;
	private LocalDateTime createdDate;
	private Long countryFk;
}
