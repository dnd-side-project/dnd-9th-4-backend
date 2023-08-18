package com.dnd.health.global.infra.feign.dto.response;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.OAuth2Provider;
import com.dnd.health.domain.member.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 서버에서 어떤 정보를 가져올지
 */
@Setter
@NoArgsConstructor
public class KakaoUserInfoResponse {

    @Getter
    private String id;
    private String expires_in;
    private String app_id;

    @Getter
    private KakaoAccount kakao_account;

    @Getter
    static class KakaoAccount {
        private Profile profile;
        private String email;
        private String age;
        private String birthDay;
        private String gender;
    }

    @Getter
    static class Profile {
        private String nickname;
        private String profile_image_url;
    }

    @Override
    public String toString() {
        return
                "id = " + this.id +
                        "profile.nickname = " + this.kakao_account.profile.nickname +
                        "profile.profile_img_url = " + this.kakao_account.profile.profile_image_url +
                        "kakao.account.email = " + this.kakao_account.email +
                        "kakao.account.age = " + this.kakao_account.age +
                        "kakao.account.birth = " + this.kakao_account.birthDay +
                        "kakao.account.gender = " + this.kakao_account.gender;
    }

    public String getUsername() {
        return this.getKakao_account().getProfile().getNickname();
    }

    public String getProfileImg() {
        return this.getKakao_account().getProfile().profile_image_url;
    }

    public String getEmail() {
        return this.getKakao_account().getEmail();
    }

    public String getAge() {
        return this.getKakao_account().getAge();
    }

    public String getGender() {
        return this.getKakao_account().gender;
    }

    public String getBirth() {
        return this.getKakao_account().getBirthDay();
    }

    public String createUserNumber() {
        return String.format("%s#%s", OAuth2Provider.KAKAO, this.getId());
    }

    public Member toEntity() {
        return Member.builder()
                .username(this.getUsername())
                .role(Role.ROLE_MEMBER)
                .oAuth2Provider(OAuth2Provider.KAKAO)
                .profileUrl(this.getProfileImg())
                .email(this.getEmail())
                .birth(this.getBirth())
                .age(this.getAge())
                .gender(this.getGender())
                .password(createUserNumber())
                .providerId(this.getId())
                .build();
    }
}
