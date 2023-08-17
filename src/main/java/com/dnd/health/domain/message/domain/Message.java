package com.dnd.health.domain.message.domain;

import com.dnd.health.domain.member.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreatedDate
    @Column(name = "sent_date", updatable = false)
    private LocalDateTime sentDate;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "member_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "member_id")
    private Member receiver;

    @PrePersist
    public void onPrePersist() {
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        this.sentDate = parsedCreateDate;
    }
}
