package com.vit.codechef.courtOfJustice.entity;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Vote entity.
 */
@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"case_id", "juror_id"}) // Prevents double voting
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private LegalCaseEntity legalCaseEntity;

    @ManyToOne
    @JoinColumn(name = "juror_id")
    private UserEntity juror;

    @Enumerated(EnumType.STRING)
    private ServiceConstants.Verdict verdict;
}