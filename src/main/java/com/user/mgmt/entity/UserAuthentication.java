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
@Table(name = "user_auth")
public class UserAuthentication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_pk")
	private Long authPk;
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "password_expiry_date", nullable = false)
	private String passwordExpiryDate;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String modifiedBy;
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
}
