package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.post.application.dto.PostUpdateCommand;
import com.dnd.health.domain.profile.domain.Sport;

import java.util.List;

public class PostUpdateRequest {

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

    public PostUpdateCommand toCommand(Long postId) {
        return new PostUpdateCommand(
                postId,
                sport,
                exerciseStyles,
                interests,
                title,
                content,
                region,
                gender,
                age,
                runtime
        );
    }
}
