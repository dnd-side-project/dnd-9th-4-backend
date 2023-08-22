package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.profile.application.dto.ProfileUpdateCommand;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileUpdateRequest {

    private String username;

    private String introduce;

    private String profileImg;

    private String gender;

    private List<String> sport;

    private String periodEx;

    private String region;

    private String mbti;

    private String wantedGender;

    private String wantedAge;

    private String wantedPersonality;

    private String wantedPeriodEx;

    private List<String> exerciseStyles;

    private List<String> interests;

    public ProfileUpdateCommand toCommand(Long memberId) {
        return new ProfileUpdateCommand(
                memberId,
                username,
                introduce,
                profileImg,
                gender,
                sport,
                periodEx,
                region,
                mbti,
                wantedGender,
                wantedAge,
                wantedPersonality,
                wantedPeriodEx,
                exerciseStyles,
                interests
        );
    }
}
