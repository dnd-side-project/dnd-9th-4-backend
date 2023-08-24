package com.dnd.health.domain.message.presentation.dto;

import com.dnd.health.domain.message.application.dto.MessageSendCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageSendRequest {

    private Long receiverId;

    private String content;

    public MessageSendCommand toCommand(Long senderId) {
        return new MessageSendCommand(senderId, receiverId, content);
    }
}
