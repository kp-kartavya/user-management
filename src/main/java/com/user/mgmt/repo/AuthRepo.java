package com.user.mgmt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.UserAuthentication;

public interface AuthRepo extends JpaRepository<UserAuthentication, Long> {
	UserAuthentication findByUsername(String username);
}
