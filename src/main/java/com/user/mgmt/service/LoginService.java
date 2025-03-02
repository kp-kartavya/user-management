package com.user.mgmt.service;

import java.text.ParseException;

public interface LoginService {
	public boolean defaultPassword(String username, String password) throws ParseException;
}
