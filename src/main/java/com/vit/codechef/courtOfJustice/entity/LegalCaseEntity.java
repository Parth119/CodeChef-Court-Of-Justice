package com.vit.codechef.courtOfJustice.entity;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import jakarta.persistence.*;

@Entity
@Table(name = "cases")
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