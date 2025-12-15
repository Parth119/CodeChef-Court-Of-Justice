package com.vit.codechef.courtOfJustice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Vote tally response dto.
 */
@Data
@AllArgsConstructor
public class VoteTallyResponseDTO {
    private Long caseId;
    private long guiltyVotes;
    private long notGuiltyVotes;
}
