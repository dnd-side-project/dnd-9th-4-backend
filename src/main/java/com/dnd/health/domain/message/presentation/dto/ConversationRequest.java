package com.dnd.health.domain.message.presentation.dto;

import lombok.Getter;

@Getter
public class ConversationRequest {

    private Long memberId;

    private Long receiverId;
}
