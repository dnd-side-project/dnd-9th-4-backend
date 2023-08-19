package com.dnd.health.domain.profile.domain;

import com.dnd.health.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rater_id", referencedColumnName = "member_id")
    private Member rater;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "member_id")
    private Member target;

    @Column(name = "score")
    private int score;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private List<ReviewMessage> reviewMessages = new ArrayList<>();
}
