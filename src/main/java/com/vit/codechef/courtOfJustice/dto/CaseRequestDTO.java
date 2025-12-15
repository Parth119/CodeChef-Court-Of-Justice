package com.vit.codechef.courtOfJustice.dto;

import lombok.Data;

/**
 * The type Case request dto.
 */
@Data
public class CaseRequestDTO {
    private String title;
    private String argumentDescription;
    private String evidenceUrl;
}