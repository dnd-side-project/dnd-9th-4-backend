package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.profile.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateResponse {

    private Long memberId;

    private String username;

    private String profileImg;

    public MateResponse(Profile profile, Member member) {
        memberId = profile.getMember().getId();
        username = member.getUsername().to();
        profileImg = profile.getProfileImg();
    }
}
