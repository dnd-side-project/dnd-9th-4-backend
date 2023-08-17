package com.dnd.health.domain.profile.domain;

import com.dnd.health.domain.common.Sport;
import com.dnd.health.domain.history.History;
import com.dnd.health.domain.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "profile")
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
    @Column(name = "sport_name")
    private Sport sport;

    @Embedded
    private PeriodEx periodEx;

    private String profileImg;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "style_id", referencedColumnName = "post_id")
    private List<ExerciseStyle> exerciseStyles = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "interest_id", referencedColumnName = "post_id")
    private List<Interest> interests = new ArrayList<>();

}
