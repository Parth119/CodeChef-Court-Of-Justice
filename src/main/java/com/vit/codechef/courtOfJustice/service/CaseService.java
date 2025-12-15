package com.vit.codechef.courtOfJustice.service;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import com.vit.codechef.courtOfJustice.dto.CaseRequestDTO;
import com.vit.codechef.courtOfJustice.dto.CaseResponseDTO;
import com.vit.codechef.courtOfJustice.entity.LegalCaseEntity;
import com.vit.codechef.courtOfJustice.entity.UserEntity;
import com.vit.codechef.courtOfJustice.repository.CaseRepository;
import com.vit.codechef.courtOfJustice.repository.UserRepository;
import com.vit.codechef.courtOfJustice.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Case service.
 */
@Service
@RequiredArgsConstructor
public class CaseService {

    private final VoteRepository voteRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    /**
     * Submit case case response dto.
     *
     * @param request  the request
     * @param username the username
     * @return the case response dto
     */
// 1. Submit a Case
    public CaseResponseDTO submitCase(CaseRequestDTO request, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LegalCaseEntity newCase = new LegalCaseEntity();
        newCase.setTitle(request.getTitle());
        newCase.setArgumentDescription(request.getArgumentDescription());
        newCase.setEvidenceUrl(request.getEvidenceUrl());
        newCase.setStatus(ServiceConstants.CaseStatus.PENDING); // Default is Pending
        newCase.setSubmittedBy(user);
        newCase.setCreatedAt(LocalDateTime.now());

        LegalCaseEntity savedCase = caseRepository.save(newCase);
        return mapToResponse(savedCase);
    }

    /**
     * Gets all cases.
     *
     * @param username the username
     * @return the all cases
     */
// 2. Get All Cases (Smart Logic: Judges see all, Jurors see only Approved)
    public List<CaseResponseDTO> getAllCases(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        List<LegalCaseEntity> cases;

        if (user.getRole() == ServiceConstants.Role.JUDGE) {
            cases = caseRepository.findAll(); // Judge sees everything
        } else {
            cases = caseRepository.findByStatus(ServiceConstants.CaseStatus.APPROVED); // Others see approved only
        }

        return cases.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Update case case response dto.
     *
     * @param id      the id
     * @param request the request
     * @return the case response dto
     */
// 3. Judge: Update Case
    public CaseResponseDTO updateCase(Long id, CaseRequestDTO request) {
        LegalCaseEntity legalCase = caseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        legalCase.setTitle(request.getTitle());
        legalCase.setArgumentDescription(request.getArgumentDescription());
        // Only update evidence if provided
        if (request.getEvidenceUrl() != null) {
            legalCase.setEvidenceUrl(request.getEvidenceUrl());
        }

        return mapToResponse(caseRepository.save(legalCase));
    }

    /**
     * Change status case response dto.
     *
     * @param id     the id
     * @param status the status
     * @return the case response dto
     */
// 4. Judge: Change Status (Approve/Reject)
    public CaseResponseDTO changeStatus(Long id, ServiceConstants.CaseStatus status) {
        LegalCaseEntity legalCase = caseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Case not found"));

        legalCase.setStatus(status);
        return mapToResponse(caseRepository.save(legalCase));
    }

    /**
     * Delete case.
     *
     * @param id the id
     */
// 5. Judge: Delete Case
    public void deleteCase(Long id) {
        if (!caseRepository.existsById(id)) {
            throw new RuntimeException("Case not found");
        }
        caseRepository.deleteById(id);
    }


    // Helper method to convert Entity -> DTO
    private CaseResponseDTO mapToResponse(LegalCaseEntity entity) {
        CaseResponseDTO response = new CaseResponseDTO();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setArgumentDescription(entity.getArgumentDescription());
        response.setEvidenceUrl(entity.getEvidenceUrl());
        response.setStatus(entity.getStatus());
        response.setSubmittedBy(entity.getSubmittedBy().getUsername());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}