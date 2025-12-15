package com.vit.codechef.courtOfJustice.dto;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import lombok.Data;

/**
 * The type Vote request dto.
 */
@Data
public class VoteRequestDTO {
    private ServiceConstants.Verdict verdict; // GUILTY or NOT_GUILTY
}
