package com.user.mgmt.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "state_master")
public class State {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_pk")
	private Long statePk;
	@Column(name = "state_name", nullable = false, unique = true)
	private String stateName;
	@CreationTimestamp
	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;
	@Column(name = "country_fk", nullable = false)
	private Long countryFk;
}
