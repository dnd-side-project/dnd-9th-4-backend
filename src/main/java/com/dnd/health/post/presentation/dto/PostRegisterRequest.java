package com.dnd.health.post.presentation.dto;

import com.dnd.health.post.application.dto.PostRegisterCommand;
import com.dnd.health.profile.domain.Sport;

import java.util.List;

public class PostRegisterRequest {

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

    public PostRegisterCommand toCommand(Long memberId) {
        return new PostRegisterCommand(
                memberId,
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
