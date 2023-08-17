package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.*;
import com.dnd.health.domain.profile.domain.Region;
import com.dnd.health.domain.profile.domain.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostRegisterCommand {

    private long memberId;

    private String sport;

    private List<String> tags;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    public Post toDomain(Member member) {
        return Post.builder()
                .member(member)
                .title(Title.from(title))
                .content(Content.from(content))
                .tags(tags.stream().map(PostTag::new).collect(Collectors.toList()))
                .sport(Sport.from(sport))
                .region(Region.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }

}
