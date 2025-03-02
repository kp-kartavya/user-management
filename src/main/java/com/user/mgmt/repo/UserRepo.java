package com.user.mgmt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
}
