package com.dnd.health.domain.post.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Wanted {

    private String age;

    private String gender;

    private String runtime;

    private String periodEx;

    protected Wanted() {
    }

    private Wanted(String age, String gender, String runtime) {
        this.age = age;
        this.gender = gender;
        this.runtime = runtime;
    }

    private Wanted(String age, String gender, String runtime, String periodEx) {
        this.age = age;
        this.gender = gender;
        this.runtime = runtime;
        this.periodEx = periodEx;
    }

    public static Wanted from(final String age, final String gender, final String runtime) {
        return new Wanted(age, gender, runtime);
    }

    public static Wanted from(final String age, final String gender, final String runtime, final String periodEx) {
        return new Wanted(age, gender, runtime, periodEx);
    }
}
