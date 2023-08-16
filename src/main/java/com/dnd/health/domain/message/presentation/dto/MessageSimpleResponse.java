package com.dnd.health.domain.message.presentation.dto;

import com.dnd.health.domain.message.domain.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageSimpleResponse {

    private Long messageId;

    private Long receiverId;

    private String username;

    private String profileImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentDate;

    private String lastMessage;

    public MessageSimpleResponse(Message message, boolean amISender) {
        this.messageId = message.getId();
        this.sentDate = message.getSentDate();
        this.lastMessage = message.getContent();
        if(amISender) {
            this.receiverId = message.getReceiver().getId();
            this.username = message.getReceiver().getUsername().to();
            this.profileImg = message.getReceiver().getProfile().getProfileImg();
        }
        else {
            this.receiverId = message.getSender().getId();
            this.username = message.getSender().getUsername().to();
            this.profileImg = message.getSender().getProfile().getProfileImg();
        }
    }
}
