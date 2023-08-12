package com.dnd.health.domain.member.domain;

import javax.persistence.Column;

public class Provider {

    @Column(name = "provider")
    private String value;

    protected Provider() {
    }

    private Provider(String value) {
        this.value = value;
    }

    public static Provider from(final String provider) {
        return new Provider(provider);
    }
}
