package com.vit.codechef.courtOfJustice.entity;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Legal case entity.
 */
@Entity
@Table(name = "cases")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LegalCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String argument; // The plaintext argument

    private String evidenceUrl; // Path to uploaded file

    @Enumerated(EnumType.STRING)
    private ServiceConstants.CaseStatus status = ServiceConstants.CaseStatus.PENDING; // Default to Pending for Judge review

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner; // The Plaintiff or Defendant
}