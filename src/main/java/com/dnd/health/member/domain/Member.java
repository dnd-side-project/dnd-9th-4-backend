package com.dnd.health.member.domain;

import com.dnd.health.message.Message;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    private long memberInfoId;

    @Embedded
    private ProviderId providerId;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Region region;

    @Embedded
    private PeriodEx periodEx;

    @Embedded
    private Sport sport;

    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages;

    @Builder
    private Member(final String username, final String age, final String providerId,
                   final String sport, final String region, final Role role,
                   final String periodEx) {
        this.username = Username.from(username);
        this.age = Age.from(age);
        this.providerId = ProviderId.from(providerId);
        this.sport = Sport.from(sport);
        this.region = Region.from(region);
        this.periodEx = PeriodEx.from(periodEx);
        this.role = role;
    }

    public void changePassword(String newPassword) {
        this.password = Password.from(newPassword);
    }

    public void changeRole(Role newRole) {
        this.role = newRole;
    }
}
