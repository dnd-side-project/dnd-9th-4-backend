package com.dnd.health.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Birth {
    @Column(name = "birth")
    private String value;

    protected Birth() {
    }

    private Birth(String value) {
        this.value = value;
    }

    public static Birth from(final String age) {
        return new Birth(age);
    }
}
