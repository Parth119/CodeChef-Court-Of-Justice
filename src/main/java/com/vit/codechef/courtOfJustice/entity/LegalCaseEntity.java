package com.vit.codechef.courtOfJustice.entity;

import com.vit.codechef.courtOfJustice.constants.ServiceConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Legal case entity.
 */
@Entity
@Table(name = "cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegalCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String argumentDescription; // The plaintext argument

    private String evidenceUrl; // Path to uploaded file (optional)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceConstants.CaseStatus status = ServiceConstants.CaseStatus.PENDING;

    // Relationship: Many cases can belong to one User (Defendant/Plaintiff)
    @ManyToOne(fetch = FetchType.EAGER) // Eager loading helps fetch username easily
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity submittedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * On create.
     */
// Automatically set the timestamp when the case is first saved
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}