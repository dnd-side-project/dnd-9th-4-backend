package com.dnd.health.global.jwt.dto;

import com.dnd.health.domain.member.domain.Member;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SessionUser implements Serializable {

    private final long id;
    private final String authority;

    public SessionUser(Member member) {
        this.id = member.getId();
        this.authority = member.getRole().getAuthority();
    }

}