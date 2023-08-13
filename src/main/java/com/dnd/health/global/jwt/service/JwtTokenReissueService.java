package com.dnd.health.global.jwt.service;

import com.dnd.health.global.infra.redis.RedisService;
import com.dnd.health.global.jwt.dto.ReIssueToken;
import com.dnd.health.domain.member.application.MemberService;
import com.dnd.health.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 토큰을 재발행할 수 있는 서비스.
 * refreshToken 이 3일이상 남으면 accessToken 만 발급합니다.
 * refreshToken 만료기간이 3일보다 적게 남으면 accessToken과 refreshToken 모두 발급합니다.
 */
@Service
@RequiredArgsConstructor
public class JwtTokenReissueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final MemberService memberService;

    public ReIssueToken reIssueToken(String refreshToken) {
        long memberId = redisService.findMemberByToken(refreshToken);
        Member member = memberService.getMemberById(memberId);
        String newAccessToken = jwtTokenProvider.createAccessToken(memberId, member.getRole());

        if(jwtTokenProvider.isTokenExpirationSafe(refreshToken)) {
            return new ReIssueToken(newAccessToken, refreshToken);
        }
        String newRefreshToken = jwtTokenProvider.createRefreshToken(memberId);
        redisService.saveRefreshToken(memberId, newRefreshToken);
        return new ReIssueToken(newAccessToken, newRefreshToken);
    }

}
