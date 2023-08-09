package com.dnd.health.member.domain;

import com.dnd.health.message.Message;
import com.dnd.health.profile.domain.Profile;
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
import javax.persistence.OneToOne;
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

    @Embedded
    private Provider provider;

    @Embedded
    private ProviderId providerId;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @Embedded
    private Age age;

    @Embedded
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Embedded
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Profile profile;

    @Builder
    private Member(final String username, final String age, final String providerId,
                   final Role role, final String password, final String email,
                   final String provider, final String gender) {
        this.provider = Provider.from(provider);
        this.providerId = ProviderId.from(providerId);
        this.username = Username.from(username);
        this.password = Password.from(password);
        this.email = Email.from(email);
        this.age = Age.from(age);
        this.gender = Gender.from(gender);
        this.role = role;
    }

    public void changeRefreshToken(String token) {
        this.refreshToken = RefreshToken.from(token);
    }

    public void changePassword(String newPassword) {
        this.password = Password.from(newPassword);
    }

    public void changeRole(Role newRole) {
        this.role = newRole;
    }
}
