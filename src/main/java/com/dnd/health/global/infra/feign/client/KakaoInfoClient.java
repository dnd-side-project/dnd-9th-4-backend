package com.dnd.health.global.infra.feign.client;

import com.dnd.health.global.infra.feign.config.KakaoFeignConfig;
import com.dnd.health.global.infra.feign.dto.response.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 카카오 사용자 정보 얻어오는 feign client
 */
@FeignClient(name = "kakaoInfoClient", url = "https://kapi.kakao.com", configuration = KakaoFeignConfig.class)
@Component
public interface KakaoInfoClient {

    //https://kapi.kakao.com/v2/user/me => 회원 정보 요청 url
    @GetMapping(value = "/v2/user/me")
    KakaoUserInfoResponse getUserInfo(@RequestHeader(name = "Authorization") String Authorization);

}

