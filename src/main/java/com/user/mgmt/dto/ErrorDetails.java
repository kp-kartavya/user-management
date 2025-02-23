package com.user.mgmt.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
	private int status;
	private Date timestamp;
	private String errorMessage;
	private Object errorBody;
}
