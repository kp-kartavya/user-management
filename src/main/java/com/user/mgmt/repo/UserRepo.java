package com.user.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
}
