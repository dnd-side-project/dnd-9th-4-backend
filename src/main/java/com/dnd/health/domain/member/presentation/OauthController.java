package com.dnd.health.domain.member.presentation;

import com.dnd.health.domain.member.application.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "소셜 로그인 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
public class OauthController {
    OAuthService oauthService;

//    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인")
//    @ResponseBody
//    @GetMapping("/api/v1/kakao")
//    public void kakaoCallback(@ApiParam(value = "kakao auth code", required = true) @RequestParam String code) {
//        String accessToken = oauthService.getKakaoAccessToken(code);
//        return BaseResponse(oauthService.kakaoLogin(accessToken).getStatus(), "요청 성공했습니다.", oauthService.kakaoLogin(accessToken));
//    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인")
    @ResponseBody
    @GetMapping("/api/kakao/login")
    public ResponseEntity<HashMap<String, Object>> kakaoCallback(@ApiParam(value = "kakao auth code", required = true) @RequestParam String code) {
        String accessToken = oauthService.getKakaoAccessToken(code);
        return new ResponseEntity<>(oauthService.getUserKakaoInfo(accessToken), HttpStatus.OK);
    }
}
