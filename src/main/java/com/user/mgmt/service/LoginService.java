package com.user.mgmt.service;

import java.text.ParseException;

import com.user.mgmt.dto.LoginDto;

public interface LoginService {
	public boolean defaultPassword(String username, String password) throws ParseException;

	public void changePassword(String username, String password);
	
	public String signin(LoginDto loginDto);
}
