package com.dnd.health.domain.message.presentation;

import com.dnd.health.domain.message.application.MessageService;
import com.dnd.health.domain.message.presentation.dto.ConversationResponse;
import com.dnd.health.domain.message.presentation.dto.MessageResponse;
import com.dnd.health.domain.message.presentation.dto.MessageSendRequest;
import com.dnd.health.domain.message.presentation.dto.MessageSimpleResponse;
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
        Long memberId = 1L;
        messageService.sendMessage(messageRequest.toCommand(memberId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MessageSimpleResponse>> getAllMessages() {
        Long memberId = 1L;
        List<MessageSimpleResponse> messageResponses = messageService.getAllMessages(memberId);
        return ResponseEntity.ok(messageResponses);
    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<ConversationResponse> getConversation(@PathVariable Long receiverId) {
        Long memberId = 1L;
        ConversationResponse conversation = messageService.getConversation(memberId, receiverId);
        return ResponseEntity.ok(conversation);
    }

}
