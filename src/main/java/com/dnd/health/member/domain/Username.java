package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Username {
    @Column(name = "username")
    private String value;

    protected Username() {
    }

    private Username(String value) {
        this.value = value;
    }

    public static Username from(final String nickname) {
        return new Username(nickname);
    }

    public String to(){
        return this.value;
    }
}
