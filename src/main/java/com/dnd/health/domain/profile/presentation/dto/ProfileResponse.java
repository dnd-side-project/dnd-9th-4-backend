package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.common.Sport;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.presentation.dto.PostResponse;
import com.dnd.health.domain.profile.domain.ExerciseStyle;
import com.dnd.health.domain.profile.domain.Interest;
import com.dnd.health.domain.profile.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ProfileResponse {

    private Long profileId;

    private Long memberId;

    private String introduce;

    private String username;

    private String age;

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

    private String gpa;

    private List<String> reviews;

    private List<RecruitedPostResponse> posts;

    public ProfileResponse(Profile profile, String gpa, List<Map.Entry<String, Integer>> reviews, List<Post> posts) {
        this.profileId = profile.getId();
        this.memberId = profile.getMember().getId();
        this.introduce = profile.getIntroduce();
        this.username = profile.getMember().getUsername().to();
        this.age = profile.getMember().getAge().to();
        this.profileImg = profile.getProfileImg();
        this.gender = profile.getMember().getGender().to();
        this.sport = profile.getSport().stream().map(Sport::name).collect(Collectors.toList());
        this.periodEx = profile.getPeriodEx().to();
        this.region = profile.getRegion().to();
        this.mbti = profile.getMbti().to();
        this.wantedAge = profile.getWantedMate().getAge();
        this.wantedGender = profile.getWantedMate().getGender();
        this.wantedPeriodEx = profile.getWantedMate().getPeriodEx();
        this.wantedPersonality = profile.getWantedMate().getPersonality();
        this.exerciseStyles = profile.getExerciseStyles().stream().map(ExerciseStyle::getValue).collect(Collectors.toList());
        this.interests = profile.getInterests().stream().map(Interest::getValue).collect(Collectors.toList());
        this.gpa = gpa;
        this.reviews = reviews.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        this.posts = posts.stream().map(RecruitedPostResponse::new).collect(Collectors.toList());
    }
}
