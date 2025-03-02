package com.user.mgmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.user.mgmt.entity.InitiatorDetails;

public interface InitiatorDetailsRepo extends JpaRepository<InitiatorDetails, Long> {
	@Query(nativeQuery = true, name = "SELECT COLUMN_NAME, NEW_VALUE, NEW_VALUE_DESC, OLD_VALUE, OLD_VALUE_DESC FROM INITIATOR_DETAILS WHERE TEMP_FK = ?")
	List<InitiatorDetails> findSelectedColumnsByTempFk(Long tempFk);
}
