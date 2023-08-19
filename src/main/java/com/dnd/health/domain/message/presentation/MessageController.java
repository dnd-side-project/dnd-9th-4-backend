package com.dnd.health.domain.message.presentation;

import com.dnd.health.domain.message.application.MessageService;
import com.dnd.health.domain.message.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<Void> sendMessage(@RequestBody MessageSendRequest messageRequest) {
        messageService.sendMessage(messageRequest.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MessageSimpleResponse>> getAllMessages(@RequestBody MessageQueryRequest messageRequest) {
        List<MessageSimpleResponse> messageResponses = messageService.getAllMessages(messageRequest.getMemberId());
        return ResponseEntity.ok(messageResponses);
    }

    @GetMapping("/conversation")
    public ResponseEntity<ConversationResponse> getConversation(@RequestBody ConversationRequest conversationRequest) {
        ConversationResponse conversation = messageService.getConversation(conversationRequest.getMemberId(), conversationRequest.getReceiverId());
        return ResponseEntity.ok(conversation);
    }

}
