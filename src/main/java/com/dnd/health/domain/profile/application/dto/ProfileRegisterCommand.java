package com.dnd.health.domain.profile.application.dto;

import com.dnd.health.domain.common.Sport;
import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.profile.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ProfileRegisterCommand {

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

    public Profile toDomain(Member member) {
        return Profile.builder()
                .member(member)
                .profileImg(profileImg)
                .sport(sport.stream().map(s -> Sport.from(s)).collect(Collectors.toList()))
                .periodEx(PeriodEx.from(periodEx))
                .region(Region.from(region))
                .mbti(Mbti.from(mbti))
                .exerciseStyles(exerciseStyles.stream().map(ExerciseStyle::new).collect(Collectors.toList()))
                .interests(interests.stream().map(Interest::new).collect(Collectors.toList()))
                .wantedMate(WantedMate.from(wantedAge, wantedGender, wantedPersonality, wantedPeriodEx))
                .build();
    }

}
