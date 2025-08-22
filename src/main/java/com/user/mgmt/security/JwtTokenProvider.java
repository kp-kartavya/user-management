package com.user.mgmt.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.user.mgmt.entity.User;
import com.user.mgmt.repo.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LogManager.getLogger(JwtTokenProvider.class);
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.expiration}")
	private long jwtExpiration;
	@Autowired
	private UserRepo userRepo;

	public String generateToken(Authentication auth) {
		String username = ((UserDetails) auth.getPrincipal()).getUsername();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
		Date current = new Date();
		Date expired = new Date(current.getTime() + jwtExpiration);
		String token = Jwts.builder().subject(username).claim("role", user.getRole().toUpperCase()).issuedAt(current)
				.expiration(expired).signWith(key()).compact();
		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUsername(String token) {
		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;
		} catch (Exception e) {
			logger.error("Exception :: " + e);
		}
		return false;
	}
}