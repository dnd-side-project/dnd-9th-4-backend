package com.dnd.health.domain.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileUrl {

    @Column(name = "profile_url")
    private String value;

    protected ProfileUrl() {
    }

    private ProfileUrl(String value) {
        this.value = value;
    }

    public static ProfileUrl from(final String profileUrl) {
        return new ProfileUrl(profileUrl);
    }
}
