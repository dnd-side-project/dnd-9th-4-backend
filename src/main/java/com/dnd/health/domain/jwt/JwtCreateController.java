package com.dnd.health.domain.jwt;

import static com.dnd.health.domain.member.application.MemberSignUpService.setCookieAndHeader;

import com.dnd.health.domain.jwt.dto.ReIssueToken;
import com.dnd.health.domain.jwt.service.JwtTokenReissueService;
import com.dnd.health.global.response.MessageResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 리프래쉬 토큰 사용 X
 */
@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final JwtTokenReissueService jwtTokenReissueService;

//    @GetMapping("/api/v1/jwt/refresh")
//    @ApiOperation(value = "Jwt 재발급", notes = "Jwt를 재발급 할 수 있습니다.")
//    public ResponseEntity<MessageResponse> reIssueToken(@CookieValue(name = "refreshToken") String refreshToken) {
//        ReIssueToken reIssueTokenDto = jwtTokenReissueService.reIssueToken(refreshToken);
//        HttpHeaders headers = setCookieAndHeader(reIssueTokenDto);
//        return new ResponseEntity<>(
//                MessageResponse.of(HttpStatus.CREATED, "Token 재발급 성공"), headers, HttpStatus.CREATED);
//    }
}
