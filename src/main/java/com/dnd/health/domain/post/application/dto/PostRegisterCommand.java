package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.*;
import com.dnd.health.domain.common.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
                .region(PostRegion.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }

}
