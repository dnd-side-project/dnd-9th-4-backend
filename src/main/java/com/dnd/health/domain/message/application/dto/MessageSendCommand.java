package com.dnd.health.domain.message.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.message.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageSendCommand {

    private Long senderId;

    private Long receiverId;

    private String content;

    public Message toDomain(Member sender, Member receiver) {
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build();
    }
}
