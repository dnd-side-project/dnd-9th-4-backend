package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RefreshToken {
    @Column(name = "refresh_token")
    private String value;

    protected RefreshToken() {
    }

    private RefreshToken(String value) {
        this.value = value;
    }

    public static RefreshToken from(final String refreshToken) {
        return new RefreshToken(refreshToken);
    }
}
