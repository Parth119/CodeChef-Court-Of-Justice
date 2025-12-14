package com.vit.codechef.courtOfJustice.repository;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import com.vit.codechef.courtOfJustice.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Vote repository.
 */
@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    /**
     * Exists by legal case entity id and juror id boolean.
     *
     * @param caseId  the case id
     * @param jurorId the juror id
     * @return the boolean
     */
// Check if juror already voted
    boolean existsByLegalCaseEntity_IdAndJurorId(Long caseId, Long jurorId);

    /**
     * Count by legal case entity id and verdict long.
     *
     * @param caseId  the case id
     * @param verdict the verdict
     * @return the long
     */
// Tally votes (Brownie Point)
    long countByLegalCaseEntity_IdAndVerdict(Long caseId, ServiceConstants.Verdict verdict);
}
