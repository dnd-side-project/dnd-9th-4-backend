package com.dnd.health.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dnd.health.global.security.oauth.provider.KakaoUser;
import com.dnd.health.global.security.oauth.provider.OAuthUserInfo;
import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.domain.Role;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/oauth/jwt/kakao")
    public String jwtCreate(@RequestBody Map<String, Object> data) {
        System.out.println("jwtCreate 실행됨");
        System.out.println(data.get("profileObj"));

        OAuthUserInfo googleUser =
                new KakaoUser((Map<String, Object>)data.get("profileObj"));

        Optional<Member> memberEntity =
                memberRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());

        if(memberEntity.isEmpty()) {
            Member userRequest = Member.builder()
                    .username(googleUser.getProvider()+"_"+googleUser.getProviderId())
                    .password(bCryptPasswordEncoder.encode("겟인데어"))
                    .email(googleUser.getEmail())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .role(Role.ROLE_MEMBER)
                    .build();

            memberRepository.save(userRequest);
        }

        Optional<Member> findMember = memberRepository.findByUsername(
                googleUser.getProvider() + "_" + googleUser.getProviderId());

        Member userEntity = findMember.get();

        return JWT.create()
                .withSubject(userEntity.getUsername().to())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_TOKEN_EXPIRATION))
                .withClaim("id", userEntity.getId())
                .withClaim("username", userEntity.getUsername().to())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
