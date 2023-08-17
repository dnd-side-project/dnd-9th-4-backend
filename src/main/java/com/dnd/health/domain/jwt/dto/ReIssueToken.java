package com.dnd.health.domain.jwt.dto;

import lombok.Getter;

@Getter
public class ReIssueToken {

    private final String refreshToken;

    private final String accessToken;

    public ReIssueToken(final String refreshToken, final String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
