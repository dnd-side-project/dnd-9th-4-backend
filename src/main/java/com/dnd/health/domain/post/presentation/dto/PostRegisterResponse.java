package com.dnd.health.domain.post.presentation.dto;

import lombok.Getter;

@Getter
public class PostRegisterResponse {

    private Long postId;

    public PostRegisterResponse(Long postId) {
        this.postId = postId;
    }

}
