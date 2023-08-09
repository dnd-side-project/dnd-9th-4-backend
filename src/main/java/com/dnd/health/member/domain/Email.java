package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name = "email")
    private String value;

    protected Email() {
    }

    private Email(String value) {
        this.value = value;
    }

    public static Email from(final String email) {
        return new Email(email);
    }
}
