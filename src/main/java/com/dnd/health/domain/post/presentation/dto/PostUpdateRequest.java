package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.post.application.dto.PostUpdateCommand;
import com.dnd.health.domain.common.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    private Sport sport;

    private List<String> tags;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    public PostUpdateCommand toCommand(Long postId) {
        return new PostUpdateCommand(
                postId,
                sport,
                tags,
                title,
                content,
                region,
                gender,
                age,
                runtime
        );
    }
}
