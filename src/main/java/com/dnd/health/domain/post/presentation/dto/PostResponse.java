package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.PostStatus;
import com.dnd.health.domain.profile.domain.Sport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {

    private Long id;

    private Long memberId;

    private String writerUsername;

    private String writerAge;

    private String writerGender;

    private String writerProfileImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime writtenDate;

    private Sport sport;

    private List<String> tags;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    private PostStatus status;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.memberId = post.getMember().getId();
        this.writerUsername = post.getMember().getUsername().to();
        this.writerGender = post.getMember().getGender().to();
        this.writerAge = post.getMember().getAge().to();
        this.writerProfileImg = post.getMember().getProfile().getProfileImg();
        this.writtenDate = post.getWrittenDate();
        this.sport = post.getSport();
        this.region = post.getRegion().to();
        this.tags = post.getTags().stream()
                .map(tag -> tag.getTag())
                .collect(Collectors.toList());
        this.title = post.getTitle().to();
        this.content = post.getContent().to();
        this.age = post.getWanted().getAge();
        this.gender = post.getWanted().getGender();
        this.runtime = post.getWanted().getRuntime();
        this.status = post.getStatus();
    }
}
