package com.vit.codechef.courtOfJustice.dto;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Case response dto.
 */
@Data
public class CaseResponseDTO {
    private Long id;
    private String title;
    private String argumentDescription;
    private String evidenceUrl;
    private ServiceConstants.CaseStatus status;
    private String submittedBy;
    private LocalDateTime createdAt;
}