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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "initiator_temp")
public class InitiatorTemp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "temp_pk")
	private Long tempPk;
	@Column(name = "request_type")
	private String requestType;
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "status")
	private String status;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_user")
	private String createdUser;
}
