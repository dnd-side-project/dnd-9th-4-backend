package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PeriodEx {
    @Column(name = "period_ex")
    private String value;

    protected PeriodEx() {
    }

    private PeriodEx(String value) {
        this.value = value;
    }

    public static PeriodEx from(final String periodEx) {
        return new PeriodEx(periodEx);
    }
}
