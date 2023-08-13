package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.Content;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.Title;
import com.dnd.health.domain.post.domain.Wanted;
import com.dnd.health.domain.profile.domain.Region;
import com.dnd.health.domain.profile.domain.Sport;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    public Post toDomain() {
        return Post.builder()
                .id(id)
                .title(Title.from(title))
                .content(Content.from(content))
                .createdAt(LocalDateTime.now())
                .exerciseStyles(exerciseStyles)
                .interests(interests)
                .sport(sport)
                .region(Region.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }
}
