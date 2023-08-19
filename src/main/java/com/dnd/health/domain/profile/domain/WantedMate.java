package com.dnd.health.domain.profile.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class WantedMate {

    @Column(name = "wanted_age")
    private String age;

    @Column(name = "wanted_gender")
    private String gender;

    @Column(name = "wanted_personality")
    private String personality;

    @Column(name = "wanted_periodex")
    private String periodEx;


    protected WantedMate() {
    }

    private WantedMate(String age, String gender, String personality, String periodEx) {
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.periodEx = periodEx;
    }

    public static WantedMate from(final String age, final String gender, final String personality, final String periodEx) {
        return new WantedMate(age, gender, personality, periodEx);
    }
}
