package com.dnd.health.domain.message.presentation.dto;

import com.dnd.health.domain.member.domain.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ConversationResponse {

    private Long myId;

    private String myUsername;

    private String myProfileImg;

    private Long receiverId;

    private String receiverUsername;

    private String receiverProfileImg;

    List<MessageResponse> messageResponses;

    public ConversationResponse(Member me, Member receiver, List<MessageResponse> messageResponses) {
        this.myId = me.getId();
        this.myUsername = me.getUsername().to();
        this.myProfileImg = me.getProfile().getProfileImg();
        this.receiverId = receiver.getId();
        this.receiverUsername = receiver.getUsername().to();
        this.receiverProfileImg = receiver.getProfile().getProfileImg();
        this.messageResponses = messageResponses;
    }
}
