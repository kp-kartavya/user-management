package com.user.mgmt.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.entity.User;
import com.user.mgmt.entity.UserAuthentication;
import com.user.mgmt.exception.ResourceNotFoundException;
import com.user.mgmt.repo.AuthRepo;
import com.user.mgmt.repo.UserRepo;
import com.user.mgmt.service.LoginService;
import com.user.mgmt.service.utils.Constants;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AuthRepo authRepo;
	@Autowired
	private UserRepo userRepo;

	private int salt = 16;
	private int hashLength = 32;
	private int parallelism = 4;
	private int memory = 65536;
	private int iterations = 10;

	private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(salt, hashLength, parallelism, memory,
			iterations);

	@Override
	public boolean defaultPassword(String username, String password) throws ParseException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));
		UserAuthentication userAuth = authRepo.findByUsername(user.getUsername());
		if (userAuth == null) {
			UserAuthentication auth = new UserAuthentication();
			auth.setCreatedBy(user.getCreatedBy());
			auth.setModifiedBy(user.getModifiedBy());

			String encoded = encoder.encode(password);
			auth.setPassword(encoded);
			auth.setUsername(user.getUsername());
			auth.setPasswordExpiryDate(Constants.addMonth(3));
			authRepo.save(auth);
		}
		if (encoder.matches(password, userAuth.getPassword())) {
			return true;
		}
		return false;
	}

}
