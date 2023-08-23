package com.dnd.health.domain.jwt.dto;

import com.dnd.health.domain.member.domain.Member;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SessionUser implements Serializable {

    private final String id;
    private final String authority;

    public SessionUser(Member member) {
        this.id = member.getProviderId().to();
        this.authority = member.getRole().getAuthority();
    }

}