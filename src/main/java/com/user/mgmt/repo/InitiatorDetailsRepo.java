package com.user.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.InitiatorDetails;

public interface InitiatorDetailsRepo extends JpaRepository<InitiatorDetails, Long> {

}
