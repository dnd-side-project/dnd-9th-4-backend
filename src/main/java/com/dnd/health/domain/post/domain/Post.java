package com.dnd.health.domain.post.domain;


import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.profile.domain.Region;
import com.dnd.health.domain.profile.domain.Sport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    private LocalDateTime createdAt;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Embedded
    private Wanted wanted;

    @ElementCollection(targetClass = String.class)
    private List<String> exerciseStyles;

    @ElementCollection(targetClass = String.class)
    private List<String> interests;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Embedded
    private Region region;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<Member> matchedMembers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member selectedMember;
}
