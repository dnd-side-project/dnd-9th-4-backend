package com.dnd.health.global.infra.feign.sevice;

import com.dnd.health.global.infra.feign.client.KakaoInfoClient;
import com.dnd.health.global.infra.feign.client.KakaoTokenClient;
import com.dnd.health.global.infra.feign.dto.KakaoInfo;
import com.dnd.health.global.infra.feign.dto.request.KakaoTokenRequest;
import com.dnd.health.global.infra.feign.dto.response.KakaoTokenResponse;
import com.dnd.health.global.infra.feign.dto.response.KakaoUserInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoFeignService {

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoInfo kakaoInfo;

    public HttpHeaders kakaoLogin(){
        return createHttpHeader(kakaoInfo.kakaoUrlInit());
    }

    private static HttpHeaders createHttpHeader(String requestUrl) {
        try {
            URI uri = new URI(requestUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uri);
            return httpHeaders;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 인가 코드를 가지고 토큰을 가져옵니다.
     * @param code 카카오 서버에서 내려준 인가 코드
     * @return 해당 사용자 정보 response dto
     */
    public KakaoUserInfoResponse getKakaoInfoWithToken(String code) {
        //1. 인가 코드를 가지고 토큰을 가져온다.
        String kakaoToken = getKakaoToken(code);
        //2. 해당 토큰으로 user 정보를 들고온다.
        return getKakaoInfo(kakaoToken);
    }

    /**
     * 카카오 액세스 토큰으로 유저 정보를 요청합니다.
     */
    private KakaoUserInfoResponse getKakaoInfo(String kakaoToken) {
        if (kakaoToken == null) {
            throw new IllegalArgumentException("Kakao Access Token cannot be null");
        }
        KakaoUserInfoResponse responseEntity = kakaoInfoClient.getUserInfo(kakaoToken);

        getJsonResponse(responseEntity);

        return kakaoInfoClient.getUserInfo(kakaoToken);
    }

    private void getJsonResponse(KakaoUserInfoResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            log.info("Received JSON response:\n" + jsonResponse);
        } catch (JsonProcessingException e) {
            log.info("Error converting response to JSON: " + e.getMessage());
        }
    }

    /**
     * 인가 코드를 가지고 토큰을 가져옵니다.
     * @param code 서버에서 내려주는 인가코드.
     * @return accessToken
     */
    private String getKakaoToken(String code) {
        KakaoTokenResponse token = kakaoTokenClient.getToken(
                KakaoTokenRequest.newInstance(kakaoInfo, code).toString());
        log.info("{}", token.getAccessToken());
        return token.getAccessToken();
    }
}
