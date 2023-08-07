package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Sport {
    @Column(name = "sport")
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
