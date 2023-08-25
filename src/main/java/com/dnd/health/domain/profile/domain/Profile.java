package com.dnd.health.domain.profile.domain;

import com.dnd.health.domain.common.Sport;
import com.dnd.health.domain.history.History;
import com.dnd.health.domain.member.domain.Gender;
import com.dnd.health.domain.member.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dnd.health.domain.profile.application.dto.ProfileUpdateCommand;
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
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String introduce;

    @Embedded
    private Mbti mbti;

    @Builder.Default
    @Embedded
    private Gpa gpa = Gpa.from("");

    @OneToMany(mappedBy = "profile")
    private List<History> history;

    @Embedded
    private Region region;

    @Convert(converter = SportListConverter.class)
    @Column(name = "sport_name")
    private List<Sport> sport;

    @Embedded
    private PeriodEx periodEx;

    @Column(name = "profile_img")
    private String profileImg;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private List<ExerciseStyle> exerciseStyles = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private List<Interest> interests = new ArrayList<>();

    @Embedded
    private WantedMate wantedMate;

//    public void updateInfo(ProfileUpdateCommand command) {
//        this.introduce = command.getIntroduce();
//        this.profileImg = command.getProfileImg();
//        this.sport = command.getSport().stream().map(Sport::from).collect(Collectors.toList());
//        this.periodEx = PeriodEx.from(command.getPeriodEx());
//        this.region = Region.from(command.getRegion());
//        this.mbti = Mbti.from(command.getMbti());
//        this.wantedMate = WantedMate.from(command.getWantedAge(), command.getGender(), command.getWantedPersonality(), command.getPeriodEx());
//        this.exerciseStyles = command.getExerciseStyles().stream().map(ExerciseStyle::new).collect(Collectors.toList());
//        this.interests = command.getInterests().stream().map(Interest::new).collect(Collectors.toList());
//    }

    public void updateInfo(ProfileUpdateCommand command) {
        this.introduce = command.getIntroduce();
        this.profileImg = command.getProfileImg();

        this.sport.clear();
        this.sport.addAll(command.getSport().stream().map(Sport::from).collect(Collectors.toList()));

        this.periodEx = PeriodEx.from(command.getPeriodEx());
        this.region = Region.from(command.getRegion());
        this.mbti = Mbti.from(command.getMbti());
        this.wantedMate = WantedMate.from(command.getWantedAge(), command.getWantedGender(), command.getWantedPersonality(), command.getWantedPeriodEx());

        this.exerciseStyles.clear();
        this.exerciseStyles.addAll(command.getExerciseStyles().stream().map(ExerciseStyle::new).collect(Collectors.toList()));

        this.interests.clear();
        this.interests.addAll(command.getInterests().stream().map(Interest::new).collect(Collectors.toList()));
    }


    public void setGpa(String gpa) {
        this.gpa = Gpa.from(gpa);
    }
}
