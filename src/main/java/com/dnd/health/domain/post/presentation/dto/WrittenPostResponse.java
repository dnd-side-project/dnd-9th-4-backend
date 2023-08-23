package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class WrittenPostResponse {

    private Long postId;

    private String title;

    private String region;

    private String runtime;

    private String sport;

    public WrittenPostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle().to();
        this.region = post.getRegion().to();
        this.runtime = post.getWanted().getRuntime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.sport = post.getSport().name();
    }
}
