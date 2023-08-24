package com.dnd.health.domain.profile.presentation;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.jwt.service.JwtTokenProvider;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.application.MemberService;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.profile.application.ProfileService;
import com.dnd.health.domain.profile.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final MemberInfoService memberInfoService;
    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> register(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody ProfileRegisterRequest profileRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        profileService.save(profileRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/memberId")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal SessionUser sessionUser) {
        log.info("sessionUser info = {}", sessionUser);
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        ProfileResponse profileResponse = profileService.getProfile(memberInfo.getMemberId());
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> update(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody ProfileUpdateRequest profileRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        profileService.update(profileRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/around")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MateResponse>> getMatesAround(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<MateResponse> mateResponses = profileService.getMatesAround(memberInfo.getMemberId());
        return ResponseEntity.ok(mateResponses);
    }
}
