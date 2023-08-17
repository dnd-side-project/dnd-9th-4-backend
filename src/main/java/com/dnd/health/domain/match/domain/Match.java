package com.dnd.health.domain.match.domain;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "matchs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Match {

    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "application_post_id", referencedColumnName = "post_id")
    private Post post;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "match_status")
    private MatchStatus matchStatus = MatchStatus.NOT_APPLY;

}
