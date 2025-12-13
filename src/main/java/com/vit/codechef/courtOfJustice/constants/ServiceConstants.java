package com.vit.codechef.courtOfJustice.constants;

public class ServiceConstants {

    public enum Role {
        DEFENDANT, PLAINTIFF, JUROR, JUDGE
    }

    public enum CaseStatus {
        PENDING, APPROVED, REJECTED
    }

    public enum Verdict {
        GUILTY, NOT_GUILTY
    }
}
