package com.user.mgmt.security;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.mgmt.entity.User;
import com.user.mgmt.entity.UserAuthentication;
import com.user.mgmt.repo.AuthRepo;
import com.user.mgmt.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	AuthRepo authRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
		UserAuthentication userAuthDetails = authRepo.findByUsername(username);
		Set<GrantedAuthority> authorities = Stream.of(user.getRole())
				.map((role) -> new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
				.collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), userAuthDetails.getPassword(),
				authorities);
	}

}