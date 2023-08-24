package com.dnd.health.domain.member.application;


import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.dto.response.LoginResponse;
import com.dnd.health.domain.member.dto.response.MemberSimpleInfoResponse;
import com.dnd.health.global.infra.feign.dto.response.KakaoUserInfoResponse;
import com.dnd.health.domain.jwt.dto.ReIssueToken;
import com.dnd.health.domain.jwt.service.JwtTokenProvider;
import com.dnd.health.global.util.CookieUtil;
import com.dnd.health.global.util.HttpHeaderUtil;
import com.dnd.health.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSignUpService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public LoginResponse loginGuestMember() {
        Member guest = memberService.saveGuestMember();
        return createSignUpResult(guest);
    }

    public LoginResponse loginKakaoMember(KakaoUserInfoResponse kakaoUserInfo) {
        //카카오 회원 Id를 변조해서 검사해본다.(회원 Id로 해야만 고유성을 가질 수 있기 때문에)
        String id = kakaoUserInfo.getId();
        log.info("kakaoId : {}", id);

        Optional<Member> isMember = memberRepository.findByKakaoId(id);

        //만약 존재한다면, update 친다.
        if (isMember.isPresent()) {
            Member member = isMember.get();
            updateMemberInfo(kakaoUserInfo, member);
            //토큰 새로 생성이 아닌, 이후에 refreshToken 체크해서 accessToken을 다시 발급해주는 로직을 넣어야 함.
            return createSignUpResult(member);
        }

        //존재하지 않는다면, user를 생성해서 넣어준다.
        Member member = signUp(kakaoUserInfo);
        return createSignUpResult(member);
    }

    /**
     * member 정보를 가지고 accessToken 과 refreshToken 을 생성한다.
     */
    private LoginResponse createSignUpResult(Member member) {
        String accessToken = jwtTokenProvider.createAccessToken(member.getProviderId(), member.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // refreshToken은 redis에 따로 저장해둔다.
//        jwtTokenProvider.saveRefreshTokenInRedis(member, refreshToken);
        return new LoginResponse(accessToken, refreshToken, new MemberSimpleInfoResponse(member));
    }

    private Member signUp(KakaoUserInfoResponse kakaoUserInfo) {
        Member member = kakaoUserInfo.toEntity();
        return memberService.saveInfo(member);
    }

    private void updateMemberInfo(KakaoUserInfoResponse kakaoUserInfo, Member member) {
        member.updateInfo(kakaoUserInfo.getKakao_account().getProfile().getProfile_image_url(),
                kakaoUserInfo.getKakao_account().getProfile().getNickname());
    }

    public static HttpHeaders setCookieAndHeader(LoginResponse loginResult) {
        HttpHeaders headers = new HttpHeaders();
        CookieUtil.setRefreshCookie(headers, loginResult.getRefreshToken());
        HttpHeaderUtil.setAccessToken(headers, loginResult.getAccessToken());
        return headers;
    }

    public static HttpHeaders setCookieAndHeader(ReIssueToken reIssueTokenDto) {
        HttpHeaders headers = new HttpHeaders();
        HttpHeaderUtil.setAccessToken(headers, reIssueTokenDto.getAccessToken());
        CookieUtil.setRefreshCookie(headers, reIssueTokenDto.getRefreshToken());
        return headers;
    }

}

