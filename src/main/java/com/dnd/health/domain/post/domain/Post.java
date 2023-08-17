package com.dnd.health.domain.post.domain;


import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.common.Sport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "member_id", name = "writer_id", nullable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Column(updatable = false, name = "written_date")
    @CreatedDate
    private LocalDateTime writtenDate;

    @Embedded
    private Wanted wanted;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Embedded
    private PostRegion region;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status = PostStatus.RECRUITING;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private List<PostTag> tags = new ArrayList<>();

    @PrePersist
    public void onPrePersist() {
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        this.writtenDate = parsedCreateDate;
    }
}
