package com.dnd.health.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password {
    @Column(name = "password")
    private String value;

    protected Password() {
    }

    private Password(String value) {
        this.value = value;
    }

    public static Password from(final String password) {
        return new Password(password);
    }

    public String to(){
        return this.value;
    }
}
