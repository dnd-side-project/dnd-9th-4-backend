package com.dnd.health.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Age {
    @Column(name = "age")
    private String value;

    protected Age() {
    }

    private Age(String value) {
        this.value = value;
    }

    public static Age from(final String age) {
        return new Age(age);
    }

    public String to() {
        return this.value;
    }
}
