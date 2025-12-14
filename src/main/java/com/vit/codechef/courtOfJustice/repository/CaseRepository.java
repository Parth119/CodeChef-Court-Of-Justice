package com.vit.codechef.courtOfJustice.repository;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import com.vit.codechef.courtOfJustice.entity.LegalCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Case repository.
 */
public interface CaseRepository extends JpaRepository<LegalCaseEntity, Long> {
    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
// For Jurors: only see approved cases
    List<LegalCaseEntity> findByStatus(ServiceConstants.CaseStatus status);
}
