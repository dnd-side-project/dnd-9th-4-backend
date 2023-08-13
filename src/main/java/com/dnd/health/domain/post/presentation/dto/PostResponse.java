package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.profile.domain.Sport;

import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {

    private Long id;

    private Long memberId;

    private String writerUsername;

    private String writerAge;

    private String writerGender;

    private Sport sport;

    private List<String> exerciseStyles;

    private List<String> interests;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    private String periodEx;

    private List<MemberInfoResponse> matchedMembers;

    private MemberInfoResponse selectedMember;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.memberId = post.getMember().getId();
        this.writerUsername = post.getMember().getUsername().to();
        this.writerGender = post.getMember().getGender().to();
        this.writerAge = post.getMember().getAge().to();
        this.sport = post.getSport();
        this.region = post.getRegion().to();
        this.exerciseStyles = post.getExerciseStyles();
        this.interests = post.getInterests();
        this.title = post.getTitle().to();
        this.content = post.getContent().to();
        this.age = post.getWanted().getAge();
        this.gender = post.getWanted().getGender();
        this.runtime = post.getWanted().getRuntime();
        this.matchedMembers = post.getMatchedMembers().stream()
                .map(MemberInfoResponse::new)
                .collect(Collectors.toList());
        this.selectedMember = new MemberInfoResponse(post.getSelectedMember());
    }
}
