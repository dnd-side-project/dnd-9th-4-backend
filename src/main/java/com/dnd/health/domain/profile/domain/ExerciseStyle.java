package com.dnd.health.domain.profile.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseStyle {

    @Id
    @Column(name = "exercise_style_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    public ExerciseStyle (String value) {
        this.value = value;
    }

}
