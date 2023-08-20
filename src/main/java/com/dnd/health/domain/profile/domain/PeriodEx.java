package com.dnd.health.domain.profile.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PeriodEx {
    @Column(name = "exercise_period")
    private String value;

    protected PeriodEx() {
    }

    private PeriodEx(String value) {
        this.value = value;
    }

    public static PeriodEx from(final String periodEx) {
        return new PeriodEx(periodEx);
    }

    public String to() {
        return this.value;
    }
}
