package com.dnd.health.domain.post.presentation.dto;

import com.dnd.health.domain.post.application.dto.PostRegisterCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostRegisterRequest {

    private String sport;

    private List<String> tags;

    private String title;

    private String content;

    private String region;

    private String gender;

    private String age;

    private String runtime;

    public PostRegisterCommand toCommand(Long memberId) {
        return new PostRegisterCommand(
                memberId,
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
