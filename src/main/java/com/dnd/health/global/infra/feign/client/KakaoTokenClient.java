package com.dnd.health.global.infra.feign.client;

import com.dnd.health.global.infra.feign.config.KakaoFeignConfig;
import com.dnd.health.global.infra.feign.dto.response.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 카카오 토큰 관련 feign client
 */
@FeignClient(name = "kakaoTokenClient", url = "https://kauth.kakao.com", configuration = KakaoFeignConfig.class)
@Component
public interface KakaoTokenClient {

    //https://kauth.kakao.com/oauth/token => 카카오 토큰 받기 url
    @PostMapping(value = "/oauth/token")
    KakaoTokenResponse getToken(@RequestBody String kakaoTokenRequestDto);

}
