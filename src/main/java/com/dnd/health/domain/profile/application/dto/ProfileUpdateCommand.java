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
public class ProfileUpdateCommand {

    private Long memberId;

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

}
