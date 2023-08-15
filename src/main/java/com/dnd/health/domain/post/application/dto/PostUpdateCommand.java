package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.*;
import com.dnd.health.domain.profile.domain.Region;
import com.dnd.health.domain.profile.domain.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateCommand {

    private Long id;

    private Sport sport;

    private List<String> exerciseStyles;

    private List<String> interests;

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
                .createdAt(LocalDateTime.now())
                .exerciseStyles(exerciseStyles.stream().map(ExerciseStyle::new).collect(Collectors.toList()))
                .interests(interests.stream().map(Interest::new).collect(Collectors.toList()))
                .sport(sport)
                .region(Region.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }
}
