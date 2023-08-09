package com.dnd.health.profile.domain;

import com.dnd.health.history.History;
import com.dnd.health.member.domain.Member;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Embedded
    private Sport sport;

    @Embedded
    private PeriodEx periodEx;

}
