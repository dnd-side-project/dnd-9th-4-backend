package com.dnd.health.domain.member.presentation;

import static com.dnd.health.domain.member.application.MemberSignUpService.setCookieAndHeader;

import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.application.MemberService;
import com.dnd.health.domain.member.application.MemberSignUpService;
import com.dnd.health.domain.member.dto.response.LoginResponse;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.member.dto.response.MemberSimpleInfoResponse;
import com.dnd.health.global.infra.feign.dto.response.KakaoUserInfoResponse;
import com.dnd.health.global.infra.feign.sevice.KakaoFeignService;
import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.global.response.DataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "멤버 관련 컨트롤")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final KakaoFeignService kakaoFeignService;
    private final MemberSignUpService memberSignUpService;
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;

    /**
     * 게스트 로그인 관련 컨트롤러
     */
    @ApiOperation(
            value = "게스트 회원가입",
            notes = "요청 보내면 바로 게스트가 생성되고, accessToken이 발급됩니다.")
    @PostMapping("/api/v1/guest/signup")
    public ResponseEntity<DataResponse<MemberSimpleInfoResponse>> guestLogin() {

        LoginResponse guestLoginResponse = memberSignUpService.loginGuestMember();
        HttpHeaders headers = setCookieAndHeader(guestLoginResponse);

        return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED,
                "게스트 회원 가입 성공", guestLoginResponse.getMember()), headers, HttpStatus.CREATED);
    }

    /**
     * 로그인 요청을 통해 인가코드를 redirect url로 발급 가능
     */
    @ApiOperation(
            value = "인가 코드 발급",
            notes = "해당 url을 통해 로그인 화면으로 넘어간 후, 사용자가 정보를 입력하면 redirect url에서 코드를 발급할 수 있습니다.")
    @GetMapping("/api/v1/kakao/login")
    public ResponseEntity<HttpHeaders> getKakaoAuthCode() {
        HttpHeaders httpHeaders = kakaoFeignService.kakaoLogin();
        return httpHeaders != null ?
                new ResponseEntity<>(httpHeaders,HttpStatus.SEE_OTHER):
                ResponseEntity.badRequest().build();
    }

    /**
     * 인가코드를 통해 accessToken 과 유저 정보를 가져온다.
     */
    @ApiOperation(
            value = "카카오 계정 회원가입",
            notes = "인가 코드를 입력하고 요청보내면, 사용자의 정보를 저장한 후 사용자의 Id를 확인할 수 있습니다.")
    @PostMapping("/api/v1/kakao/signup")
    public ResponseEntity<DataResponse<MemberSimpleInfoResponse>> kakaoLogin(@RequestBody Map<String, String> codeData) {
        String code = codeData.get("code");
        log.info("code : {}", code);
        //코드를 통해 액세스 토큰 발급한 후, 유저 정보를 가져온다.
        KakaoUserInfoResponse kakaoUserInfo = kakaoFeignService.getKakaoInfoWithToken(code);
        LoginResponse kakaoLoginResponse = memberSignUpService.loginKakaoMember(kakaoUserInfo);
        HttpHeaders headers = setCookieAndHeader(kakaoLoginResponse);

        ResponseEntity<DataResponse<MemberSimpleInfoResponse>> responseEntity =
                new ResponseEntity<>(
                        DataResponse.of(
                                HttpStatus.CREATED, "카카오 계정으로 회원가입 성공", kakaoLoginResponse.getMember()), headers,
                        HttpStatus.CREATED);
        log.info("response entity : {}", responseEntity);

        return responseEntity;
    }

    @DeleteMapping("api/delete/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        try {
            memberService.deleteMember(memberId);
            return ResponseEntity.ok("Member deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete member");
        }
    }

    /**
     * 유저가 자기 자신의 정보에 대해 알 수 있다.
     */
    @ApiOperation(
            value = "내 정보 조회",
            notes = "마이 페이지에서 사용자의 정보를 볼 수 있습니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/member")
    public ResponseEntity<DataResponse<MemberInfoResponse>> getMember(
            @AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());

        return new ResponseEntity<>(
                DataResponse.of(HttpStatus.OK, "멤버 정보 조회 성공", memberInfo), HttpStatus.OK);
    }
}
