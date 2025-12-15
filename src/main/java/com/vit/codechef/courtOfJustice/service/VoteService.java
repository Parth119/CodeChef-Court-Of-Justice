package com.vit.codechef.courtOfJustice.service;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import com.vit.codechef.courtOfJustice.dto.VoteRequestDTO;
import com.vit.codechef.courtOfJustice.dto.VoteTallyResponseDTO;
import com.vit.codechef.courtOfJustice.entity.LegalCaseEntity;
import com.vit.codechef.courtOfJustice.entity.UserEntity;
import com.vit.codechef.courtOfJustice.entity.VoteEntity;
import com.vit.codechef.courtOfJustice.repository.CaseRepository;
import com.vit.codechef.courtOfJustice.repository.UserRepository;
import com.vit.codechef.courtOfJustice.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Vote service.
 */
@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    /**
     * Cast vote string.
     *
     * @param caseId   the case id
     * @param username the username
     * @param request  the request
     * @return the string
     */
    public String castVote(Long caseId, String username, VoteRequestDTO request) {
        // 1. Get the Case
        LegalCaseEntity legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        // 2. Ensure Case is Approved
        if (legalCase.getStatus() != ServiceConstants.CaseStatus.APPROVED) {
            throw new RuntimeException("You cannot vote on a case that hasn't been approved by a Judge.");
        }

        // 3. Get the Juror
        UserEntity juror = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4. CHECK: Has this juror already voted on this case?
        if (voteRepository.existsByLegalCaseEntity_IdAndJurorId(caseId, juror.getId())) {
            throw new RuntimeException("Double Jeopardy! You have already voted on this case.");
        }

        // 5. Save the Vote
        VoteEntity vote = new VoteEntity();
        vote.setLegalCaseEntity(legalCase);
        vote.setJuror(juror);
        vote.setVerdict(request.getVerdict());

        voteRepository.save(vote);
        return "Vote cast successfully";
    }

    /**
     * Gets results.
     *
     * @param caseId the case id
     * @return the results
     */
    public VoteTallyResponseDTO getResults(Long caseId) {
        long guiltyCount = voteRepository.countByLegalCaseEntity_IdAndVerdict(caseId, ServiceConstants.Verdict.GUILTY);
        long notGuiltyCount = voteRepository.countByLegalCaseEntity_IdAndVerdict(caseId, ServiceConstants.Verdict.NOT_GUILTY);

        return new VoteTallyResponseDTO(caseId, guiltyCount, notGuiltyCount);
    }
}