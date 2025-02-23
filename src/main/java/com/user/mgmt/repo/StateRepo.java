package com.user.mgmt.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.State;

public interface StateRepo extends JpaRepository<State, Long> {
	boolean existsByStateName(String stateName);

	Optional<List<State>> findByCountryFk(Long countryFk);
}
