package com.dnd.health.global.infra.redis;

import static com.dnd.health.global.exception.ErrorCode.EXPIRED_REFRESH_TOKEN;

import com.dnd.health.global.jwt.handler.exception.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * refreshToken 관리를 위한 Redis 관련 서비스.
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(long memberId, String refreshToken) {
        refreshTokenRepository.save(new RefreshToken(memberId, refreshToken));
    }

    @Transactional(readOnly = true)
    public long findMemberByToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new JwtException(EXPIRED_REFRESH_TOKEN));
        return token.getMemberId();
    }
}
