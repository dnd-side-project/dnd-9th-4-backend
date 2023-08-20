package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.profile.application.dto.ProfileRegisterCommand;
import com.dnd.health.domain.profile.domain.ExerciseStyle;
import com.dnd.health.domain.profile.domain.Interest;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileRegisterRequest {

    private Long memberId;

    private String username;

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

    public ProfileRegisterCommand toCommand() {
        return new ProfileRegisterCommand(
                memberId,
                username,
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
