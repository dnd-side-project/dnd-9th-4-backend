package com.dnd.health.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_GUEST("guest"),
    ROLE_MEMBER("member"),
    ROLE_ADMIN("ADMIN");

    private final String authority;
}
