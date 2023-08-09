package com.dnd.health.config.oauth;

import com.dnd.health.config.auth.PrincipalDetails;
import com.dnd.health.config.oauth.provider.KakaoUserInfo;
import com.dnd.health.config.oauth.provider.OAuth2UserInfo;
import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.MemberRepository;
import com.dnd.health.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Slf4j
@AllArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /**
         * registrationId -> 어떤 OAuth2로 로그인 했는지 확인 가능.
         */
        log.info("userRequest = {}", userRequest.getClientRegistration());
        log.info("get Access Token = {}", userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code를 리턴 받음(OAuth-Client 라이브러리) -> AccessToken 요청
        // userRequest 정보 -> 회원 프로필 받아야함 -> loadUser 함수 -> 구글로부터 회원 프로필을 받아준다.
        log.info("get Attribute = {}", oAuth2User.getAttributes());

        // 회원 가입 강제로 진행 예정
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("kakao login request");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            log.info("did not valid sns login");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; // google_*********
        String password = bCryptPasswordEncoder.encode("new-ple");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_MEMBER";
        String age = oAuth2UserInfo.getAge();
        String gender = oAuth2UserInfo.getGender();

        Member memberEntity = memberRepository.findByUsername(username);

        if (memberEntity != null) {
            memberEntity = Member.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(Role.valueOf(role))
                    .provider(provider)
                    .providerId(providerId)
                    .age(age)
                    .gender(gender)
                    .build();
            memberRepository.save(memberEntity);
        } else {
            log.info("already logged in to social. You have automatic membership registration.");
        }
        return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());
    }
}
