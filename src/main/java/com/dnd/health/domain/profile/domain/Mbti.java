package com.dnd.health.domain.profile.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Mbti {
    @Column(name = "mbti_type")
    private String value;

    protected Mbti() {
    }

    private Mbti(String value) {
        this.value = value;
    }

    public static Mbti from(final String mbti) {
        return new Mbti(mbti);
    }

    public String to() {
        return value;
    }
}
