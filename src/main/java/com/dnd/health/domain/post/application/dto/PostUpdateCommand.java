package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.*;
import com.dnd.health.domain.common.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateCommand {

    private Long id;

    private Sport sport;

    private List<String> tags;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    public Post toDomain(Member member) {
        return Post.builder()
                .id(id)
                .member(member)
                .title(Title.from(title))
                .content(Content.from(content))
                .tags(tags.stream().map(PostTag::new).collect(Collectors.toList()))
                .sport(sport)
                .region(PostRegion.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }
}
