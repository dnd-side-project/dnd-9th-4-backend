package com.dnd.health.domain.post.domain;

import javax.persistence.Column;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Embeddable
public class Wanted {

    @Column(name = "wanted_age")
    private String age;

    @Column(name = "wanted_gender")
    private String gender;

    @Column(name = "wanted_runtime")
    private LocalDateTime runtime;

    protected Wanted() {
    }

    private Wanted(String age, String gender, String runtime) {
        this.age = age;
        this.gender = gender;
        this.runtime = LocalDateTime.parse(runtime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static Wanted from(final String age, final String gender, final String runtime) {
        return new Wanted(age, gender, runtime);
    }
}
