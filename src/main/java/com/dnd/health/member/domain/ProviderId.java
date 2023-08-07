package com.dnd.health.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProviderId {

    @Column(name = "provider_id")
    private String value;

    protected ProviderId() {
    }

    public ProviderId(final String value) {
        this.value = value;
    }

    public static ProviderId from(final String providerId) {
        return new ProviderId(providerId);
    }
}
