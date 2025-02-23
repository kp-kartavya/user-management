package com.user.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.mgmt.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Long> {
	boolean existsByCountryName(String countryName);
}
