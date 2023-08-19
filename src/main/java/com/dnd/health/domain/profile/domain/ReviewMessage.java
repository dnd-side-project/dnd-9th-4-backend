package com.dnd.health.domain.profile.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMessage {

    @Id
    @Column(name = "review_message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    public ReviewMessage(String value) {
        this.value = value;
    }
}
