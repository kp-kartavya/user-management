package com.user.mgmt.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_pk")
	private Long userPk;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "phone_number", nullable = false)
	private Long phoneNumber;
	@Column(name = "country_fk", nullable = false)
	private Long countryFk;
	@Column(name = "state_fk", nullable = false)
	private Long stateFk;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String modifiedBy;
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@Column(name = "role", nullable = false)
	private String role;

}
