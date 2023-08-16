package com.dnd.health.domain.message.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiverIdOrSenderIdOrderBySentDateDesc(Long senderId, Long receiverId);

    List<Message> findAllByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderBySentDateDesc(
            Long member1Id, Long member2Id, Long member3Id, Long member4Id);
}
