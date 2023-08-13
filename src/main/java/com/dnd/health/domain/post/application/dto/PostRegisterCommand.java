package com.dnd.health.domain.post.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.Content;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.Title;
import com.dnd.health.domain.post.domain.Wanted;
import com.dnd.health.domain.profile.domain.Region;
import com.dnd.health.domain.profile.domain.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostRegisterCommand {

    private long memberId;

    private String sport;

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
                .member(member)
                .title(Title.from(title))
                .content(Content.from(content))
                .createdAt(LocalDateTime.now())
                .exerciseStyles(exerciseStyles)
                .interests(interests)
                .sport(Sport.from(sport))
                .region(Region.from(region))
                .wanted(Wanted.from(age, gender, runtime))
                .build();
    }

}
