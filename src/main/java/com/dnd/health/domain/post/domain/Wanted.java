package com.dnd.health.domain.post.domain;

import javax.persistence.Column;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Wanted {

    @Column(name = "wanted_age")
    private String age;

    @Column(name = "wanted_gender")
    private String gender;

    @Column(name = "wanted_runtime")
    private String runtime;

    protected Wanted() {
    }

    private Wanted(String age, String gender, String runtime) {
        this.age = age;
        this.gender = gender;
        this.runtime = runtime;
    }

    public static Wanted from(final String age, final String gender, final String runtime) {
        return new Wanted(age, gender, runtime);
    }
}
