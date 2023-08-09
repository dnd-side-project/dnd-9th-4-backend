package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Gender {
    @Column(name = "gender")
    private String value;

    protected Gender() {
    }

    private Gender(String value) {
        this.value = value;
    }

    public static Gender from(final String gender) {
        return new Gender(gender);
    }
}
