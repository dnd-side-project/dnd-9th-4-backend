package com.dnd.health.domain.message.presentation;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.message.application.MessageService;
import com.dnd.health.domain.message.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/message")
public class MessageController {

    private final MemberInfoService memberInfoService;
    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<Void> sendMessage(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody MessageSendRequest messageRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        messageService.sendMessage(messageRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MessageSimpleResponse>> getAllMessages(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<MessageSimpleResponse> messageResponses = messageService.getAllMessages(memberInfo.getMemberId());
        return ResponseEntity.ok(messageResponses);
    }

    @PostMapping("/conversation")
    public ResponseEntity<ConversationResponse> getConversation(@AuthenticationPrincipal SessionUser sessionUser,@RequestBody ConversationRequest conversationRequest) {
        ConversationResponse conversation = messageService.getConversation(conversationRequest.getMemberId(), conversationRequest.getReceiverId());
        return ResponseEntity.ok(conversation);
    }

}
