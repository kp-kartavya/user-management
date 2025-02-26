package com.user.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	boolean existsByRoleName(String roleName);
}
