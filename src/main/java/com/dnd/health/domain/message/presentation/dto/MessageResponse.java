package com.dnd.health.domain.message.presentation.dto;

import com.dnd.health.domain.message.domain.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class MessageResponse {

    private Long messageId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentDate;

    private String content;

    private Sender sender;

    public MessageResponse (Message message, Long memberId) {
        this.messageId = message.getId();
        this.sentDate = message.getSentDate();
        this.content = message.getContent();
        if(message.getSender().getId() == memberId) {
            this.sender = Sender.ME;
        }
        else {
            this.sender = Sender.RECEIVER;
        }
    }

}
