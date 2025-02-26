package com.user.mgmt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.InitiatorTemp;

public interface InitiatorTempRepo extends JpaRepository<InitiatorTemp, Long> {
	boolean existsByCreatedUserAndRequestTypeAndStatus(String createdUser, String requestType, String status);

	Optional<InitiatorTemp> findByTempPk(Long tempPk);
}
