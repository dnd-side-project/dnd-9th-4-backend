package com.dnd.health.domain.member.domain;

import com.dnd.health.domain.message.domain.Message;
import com.dnd.health.domain.profile.domain.Profile;
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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "provider_id")
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth2_Provider")
    private OAuth2Provider oauth2Provider;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

    @Embedded
    private Age age;

    @Embedded
    private Birth birth;

    @Embedded
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Embedded
    private ProfileUrl profileUrl;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @Builder
    public Member(final String username, final String birth, final String providerId,
                  final Role role, final String password, final String email,
                  final String gender, final String age,
                  final OAuth2Provider oAuth2Provider, final String profileUrl) {
        this.providerId = providerId;
        this.username = Username.from(username);
        this.password = Password.from(password);
        this.email = Email.from(email);
        this.birth = Birth.from(birth);
        this.gender = Gender.from(gender);
        this.age = Age.from(age);
        this.role = role;
        this.oauth2Provider = oAuth2Provider;
        this.profileUrl = ProfileUrl.from(profileUrl);
    }

    public void changePassword(String newPassword) {
        this.password = Password.from(newPassword);
    }

    public void changeRole(Role newRole) {
        this.role = newRole;
    }

    public void updateInfo(String profileUrl, String username) {
        this.profileUrl = ProfileUrl.from(profileUrl);
        this.username = Username.from(username);
    }

    public void updateInfo(String profileUrl, String username, String gender) {
        this.profileUrl = ProfileUrl.from(profileUrl);
        this.username = Username.from(username);
        this.gender = Gender.from(gender);
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
    }
}
