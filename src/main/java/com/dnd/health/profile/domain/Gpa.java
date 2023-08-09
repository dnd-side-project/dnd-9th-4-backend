package com.dnd.health.profile.domain;

import com.dnd.health.member.domain.Age;
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
