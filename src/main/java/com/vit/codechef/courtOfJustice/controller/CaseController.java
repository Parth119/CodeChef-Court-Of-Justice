package com.vit.codechef.courtOfJustice.controller;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import com.vit.codechef.courtOfJustice.dto.CaseRequestDTO;
import com.vit.codechef.courtOfJustice.dto.CaseResponseDTO;
import com.vit.codechef.courtOfJustice.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Case controller.
 */
@RestController
@RequestMapping("/case")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    // Helper to get current logged-in username
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    /**
     * Submit case response entity.
     *
     * @param request the request
     * @return the response entity
     */
// 1. Submit Case (Defendant/Plaintiff)
    @PostMapping("/submit")
    public ResponseEntity<CaseResponseDTO> submitCase(@RequestBody CaseRequestDTO request) {
        return ResponseEntity.ok(caseService.submitCase(request, getCurrentUsername()));
    }

    /**
     * Gets all cases.
     *
     * @return the all cases
     */
// 2. View All Cases (Filtered by Role)
    @GetMapping("/all")
    public ResponseEntity<List<CaseResponseDTO>> getAllCases() {
        return ResponseEntity.ok(caseService.getAllCases(getCurrentUsername()));
    }

    /**
     * Edit case response entity.
     *
     * @param id      the id
     * @param request the request
     * @return the response entity
     */
// 3. Edit Case (Judge Only)
    @PatchMapping("/edit/{id}")
    public ResponseEntity<CaseResponseDTO> editCase(@PathVariable Long id, @RequestBody CaseRequestDTO request) {
        return ResponseEntity.ok(caseService.updateCase(id, request));
    }

    /**
     * Approve case response entity.
     *
     * @param id the id
     * @return the response entity
     */
// 4. Approve Case (Judge Only)
    @PatchMapping("/approve/{id}")
    public ResponseEntity<CaseResponseDTO> approveCase(@PathVariable Long id) {
        return ResponseEntity.ok(caseService.changeStatus(id, ServiceConstants.CaseStatus.APPROVED));
    }

    /**
     * Reject case response entity.
     *
     * @param id the id
     * @return the response entity
     */
// 5. Reject Case (Judge Only)
    @PatchMapping("/reject/{id}")
    public ResponseEntity<CaseResponseDTO> rejectCase(@PathVariable Long id) {
        return ResponseEntity.ok(caseService.changeStatus(id, ServiceConstants.CaseStatus.REJECTED));
    }

    /**
     * Delete case response entity.
     *
     * @param id the id
     * @return the response entity
     */
// 6. Delete Case (Judge Only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCase(@PathVariable Long id) {
        caseService.deleteCase(id);
        return ResponseEntity.ok("Case deleted successfully");
    }
}