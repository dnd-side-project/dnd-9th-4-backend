package com.dnd.health.domain.profile.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Gpa {
    @Column(name = "gpa_value")
    private String value;

    protected Gpa() {
    }

    private Gpa(String value) {
        this.value = value;
    }

    public static Gpa from(final String gpa) {
        return new Gpa(gpa);
    }
}
