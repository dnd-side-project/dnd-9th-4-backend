package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.profile.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MateResponse {

    private Long memberId;

    private String username;

    private String profileImg;

    public MateResponse(Profile profile) {
        memberId = profile.getMember().getId();
        username = profile.getMember().getUsername().to();
        profileImg = profile.getProfileImg();
    }
}
