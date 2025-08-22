package com.user.mgmt.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.dto.LoginDto;
import com.user.mgmt.entity.User;
import com.user.mgmt.entity.UserAuthentication;
import com.user.mgmt.exception.ResourceNotFoundException;
import com.user.mgmt.repo.AuthRepo;
import com.user.mgmt.repo.UserRepo;
import com.user.mgmt.security.JwtTokenProvider;
import com.user.mgmt.service.LoginService;
import com.user.mgmt.service.utils.Constants;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AuthRepo authRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(Constants.SALT, Constants.HASH_LENGTH,
			Constants.PARALLELISM, Constants.MEMORY, Constants.ITERATIONS);

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

	@Override
	public void changePassword(String username, String password) {
		UserAuthentication auth = authRepo.findByUsername(username);
		String encoded = encoder.encode(password);
		auth.setPassword(encoded);
		authRepo.saveAndFlush(auth);
	}

	@Override
	public String signin(LoginDto loginDto) {
		User user = userRepo.findByUsername(loginDto.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username " + loginDto.getUsername()));
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenProvider.generateToken(authentication);
	}

}
