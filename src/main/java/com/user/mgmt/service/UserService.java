package com.user.mgmt.service;

import java.text.ParseException;

import com.user.mgmt.dto.InitiatorTempDto;
import com.user.mgmt.dto.UserDto;

public interface UserService {
	InitiatorTempDto saveRequest(UserDto userDto);
	InitiatorTempDto approveRequest(Long tempPk) throws ParseException;
}
