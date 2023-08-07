package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Region {
    @Column(name = "region")
    private String value;

    protected Region() {
    }

    private Region(String value) {
        this.value = value;
    }

    public static Region from(final String region) {
        return new Region(region);
    }
}
