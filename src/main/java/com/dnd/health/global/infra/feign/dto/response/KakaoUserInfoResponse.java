package com.dnd.health.global.infra.feign.dto.response;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.OAuth2Provider;
import com.dnd.health.domain.member.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 카카오 서버에서 어떤 정보를 가져올지
 */
@Getter
@NoArgsConstructor
public class KakaoUserInfoResponse {

    @Getter
    private String id;
    @Getter
    private String created_at;

    private KakaoAccount kakao_account;

    public KakaoUserInfoResponse(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    public KakaoAccount getKakao_account() {
        return kakao_account;
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoAccount {
        private String created_at;
        private String birthday;
        private String email;
        private String gender;
        private String age_range;
        private Profile profile;

        public KakaoAccount(String created_at, String gender,
                            String birthday, String email, String age_range,
                            Profile profile) {
            this.created_at = created_at;
            this.gender = gender;
            this.birthday = birthday;
            this.email = email;
            this.age_range = age_range;
            this.profile = profile;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Profile {
        private String profile_image_url;
        private String nickname;

        public Profile(String profile_image_url, String nickname) {
            this.profile_image_url = profile_image_url;
            this.nickname = nickname;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoAccountProfile {
        private String nickname;
        private String profile_image_url;
    }

    public Member toEntity() {
        return Member.builder()
                .username(this.kakao_account.profile.getNickname())
                .birth(this.kakao_account.getBirthday())
                .providerId(this.getId())
                .email(this.kakao_account.getEmail())
                .gender(this.kakao_account.getGender())
                .age(this.kakao_account.getAge_range())
                .oAuth2Provider(OAuth2Provider.KAKAO)
                .profileUrl(this.kakao_account.profile.getProfile_image_url())
                .role(Role.ROLE_MEMBER)
                .password(createUserNumber())
                .build();
    }

    public String createUserNumber() {
        return String.format("%s#%s", OAuth2Provider.KAKAO, this.getId());
    }


    @Override
    public String toString() {
        return "id = " + this.getId() +
                ", created_at = " + this.created_at +
                ", birthday = " + (kakao_account != null ? kakao_account.birthday : "null") +
                ", email = " + (kakao_account != null ? kakao_account.email : "null") +
                ", gender = " + (kakao_account != null ? kakao_account.gender : "null") +
                ", age_range = " + (kakao_account != null ? kakao_account.age_range : "null") +
                ", profile_image_url = " + (kakao_account != null && kakao_account.profile != null ?
                kakao_account.profile.getProfile_image_url() : "null") +
                ", nickname = " + (kakao_account != null && kakao_account.profile != null ?
                kakao_account.profile.getNickname() : "null");
    }
}
