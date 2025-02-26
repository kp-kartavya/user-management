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
@Table(name = "initiator_details")
public class InitiatorDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "details_pk")
	private Long detailsPk;
	@Column(name = "temp_fk", nullable = false)
	private Long tempFk;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "created_by")
	private String createdBy;
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "new_value")
	private String newValue;
	@Column(name = "new_value_desc")
	private String newValueDesc;
	@Column(name = "old_value")
	private String oldValue;
	@Column(name = "old_value_desc")
	private String oldValueDesc;
	@Column(name = "column_name")
	private String columnName;

	public InitiatorDetails(String newValue, String newValueDesc, String oldValue, String oldValueDesc) {
		this.newValue = newValue;
		this.newValueDesc = newValueDesc;
		this.oldValue = oldValue;
		this.oldValueDesc = oldValueDesc;
	}

}
