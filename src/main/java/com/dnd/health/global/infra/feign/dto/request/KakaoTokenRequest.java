package com.dnd.health.global.infra.feign.dto.request;

import com.dnd.health.global.infra.feign.dto.KakaoInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카카오 액세스 토큰을 얻기 위한 request Dto
 * 액세스 토큰을 얻어야만 사용자 정보를 가져올 수 있다.
 */
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KakaoTokenRequest {

    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private final String grant_type = "authorization_code";

    public static KakaoTokenRequest newInstance(KakaoInfo kakaoInfo, String code) {
        return KakaoTokenRequest.builder()
                .client_id(kakaoInfo.getClientId())
                .client_secret(kakaoInfo.getSecretKey())
                .redirect_uri(kakaoInfo.getRedirectUri())
                .code(code)
                .build();
    }

    // kakao는 Content-Type 을 application/x-www-form-urlencoded 로 받는다.
    // FeignClient는 기본이 JSON으로 변경하니 아래처럼 데이터를 변환 후 보내야 한다.
    @Override
    public String toString() {
        return
                "code=" + code + '&' +
                        "client_id=" + client_id + '&' +
                        "client_secret=" + client_secret + '&' +
                        "redirect_uri=" + redirect_uri + '&' +
                        "grant_type=" + grant_type;
    }

}