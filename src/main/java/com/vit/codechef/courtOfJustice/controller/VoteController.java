package com.vit.codechef.courtOfJustice.controller;

import com.vit.codechef.courtOfJustice.dto.VoteRequestDTO;
import com.vit.codechef.courtOfJustice.dto.VoteTallyResponseDTO;
import com.vit.codechef.courtOfJustice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * The type Vote controller.
 */
@RestController
@RequestMapping("/jury")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    // Helper to get username
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Cast vote response entity.
     *
     * @param caseId  the case id
     * @param request the request
     * @return the response entity
     */
// 1. Cast Vote (Juror Only)
    @PostMapping("/vote/{caseId}")
    public ResponseEntity<String> castVote(@PathVariable Long caseId, @RequestBody VoteRequestDTO request) {
        return ResponseEntity.ok(voteService.castVote(caseId, getCurrentUsername(), request));
    }

    /**
     * Gets results.
     *
     * @param caseId the case id
     * @return the results
     */
// 2. View Results (Public/All Roles)
    @GetMapping("/results/{caseId}")
    public ResponseEntity<VoteTallyResponseDTO> getResults(@PathVariable Long caseId) {
        return ResponseEntity.ok(voteService.getResults(caseId));
    }
}
