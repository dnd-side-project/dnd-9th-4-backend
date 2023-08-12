package com.dnd.health.domain.profile.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Sport {
    @Column(name = "sport_name")
    private String value;

    protected Sport() {
    }

    private Sport(String value) {
        this.value = value;
    }

    public static Sport from(final String sport) {
        return new Sport(sport);
    }
}
