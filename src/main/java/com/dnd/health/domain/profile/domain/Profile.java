package com.dnd.health.domain.profile.domain;

import com.dnd.health.domain.history.History;
import com.dnd.health.domain.member.domain.Member;
import java.util.List;
import javax.persistence.*;

import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Mbti mbti;

    @Embedded
    private Gpa gpa;

    @OneToMany(mappedBy = "profile")
    private List<History> history;

    @Embedded
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Embedded
    private PeriodEx periodEx;

    private String profileImg;

}
