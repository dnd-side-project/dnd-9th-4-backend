package com.dnd.health.domain.message.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.message.application.dto.MessageSendCommand;
import com.dnd.health.domain.message.domain.Message;
import com.dnd.health.domain.message.domain.MessageRepository;
import com.dnd.health.domain.message.presentation.dto.ConversationResponse;
import com.dnd.health.domain.message.presentation.dto.MessageResponse;
import com.dnd.health.domain.message.presentation.dto.MessageSimpleResponse;
import com.dnd.health.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    public Long sendMessage(MessageSendCommand command) {
        Member sender = memberRepository.findById(command.getSenderId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Member receiver = memberRepository.findById(command.getReceiverId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Message message = command.toDomain(sender, receiver);
        return messageRepository.save(message).getId();
    }

    public List<MessageSimpleResponse> getAllMessages(Long memberId) {
        List<Message> allByMemberId = messageRepository.findAllByReceiverIdOrSenderIdOrderBySentDateDesc(memberId, memberId);
        List<MessageSimpleResponse> messageList = new ArrayList<>();

        if(allByMemberId.isEmpty()) return messageList;

        messageList.add(
                new MessageSimpleResponse(
                        allByMemberId.get(0),
                        allByMemberId.get(0).getSender().getId() == memberId ? true : false
                )
        );

        for(int i=1; i<allByMemberId.size(); i++) {
            Message message = allByMemberId.get(i);
            Long receiverId = message.getReceiver().getId();
            Long senderId = message.getSender().getId();
            boolean isExist = false;
            for(int j=0; j<messageList.size(); j++) {
                //내가 보낸 메시지 일 경우
                if(receiverId == messageList.get(j).getReceiverId() || senderId == messageList.get(j).getReceiverId()) {
                    isExist = true;
                    break;
                }
            }
            if(!isExist) {
                messageList.add(
                        new MessageSimpleResponse(
                                allByMemberId.get(i), senderId == memberId ? true : false));
            }
        }
        return messageList;
    }

    public ConversationResponse getConversation(Long myId, Long receiverId) {
        List<Message> allMessages = messageRepository.findAllByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderBySentDateDesc(
                myId, receiverId, myId, receiverId);

        Message message = allMessages.get(0);
        Long senderId = message.getSender().getId();
        if(senderId == myId) {
            return new ConversationResponse(
                    message.getSender(),
                    message.getReceiver(),
                    allMessages.stream()
                            .map((m) -> new MessageResponse(m, myId))
                            .collect(Collectors.toList())
            );
        }
        else {
            return new ConversationResponse(
                    message.getReceiver(),
                    message.getSender(),
                    allMessages.stream()
                            .map((m) -> new MessageResponse(m, myId))
                            .collect(Collectors.toList())
            );
        }
    }
}
