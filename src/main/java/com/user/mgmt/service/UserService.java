package com.user.mgmt.service;

import com.user.mgmt.dto.InitiatorTempDto;
import com.user.mgmt.dto.UserDto;

public interface UserService {
	InitiatorTempDto saveRequest(UserDto userDto);
}
