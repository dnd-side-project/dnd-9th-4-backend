package com.dnd.health.global.infra.feign.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 액세스 토큰 정보 응답 Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class KakaoTokenResponse {

    /**
     * 반환되는 토큰 타입(Bearer 고정)
     */
    private String token_type;

    private String access_token;

    /**
     * accessToken 만료 시간
     */
    private String expires_in;

    private String refresh_token;

    /**
     * refreshToken 만료 시간
     */
    private String refresh_token_expires_in;

    private String scope;

    private String id_token;

    public String getAccessToken() {
        return "Bearer " + access_token;
    }

    @Override
    public String toString() {
        return "액세스 토큰입니다: " + access_token;
    }

}

