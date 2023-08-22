package com.dnd.health.domain.match.presentation;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.match.application.MatchingService;
import com.dnd.health.domain.match.presentation.dto.*;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchingController {

    private final MemberInfoService memberInfoService;
    private final MatchingService matchingService;

    @PostMapping("/apply")
    public ResponseEntity<Void> apply(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody MatchingApplyRequest matchingRequest){
        matchingService.apply(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/refuse")
    public ResponseEntity<Void> refuseMatch(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody MatchingRefuseRequest matchingRequest) {
        matchingService.refuseMatch(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmMatch(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody MatchingRefuseRequest matchingRequest) {
        matchingService.confirmMatch(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<MatchApplicantResponse>> getAllApplicant(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId) {
        List<MatchApplicantResponse> applicants = matchingService.getAllApplicant(postId);
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<MatchResponse> getMatchStatus(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        MatchResponse match = matchingService.getMatch(postId, memberInfo.getMemberId());
        return ResponseEntity.ok(match);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> cancelMatch(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        matchingService.delete(postId, memberInfo.getMemberId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<MatchScheduleResponse> getMatches(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        MatchScheduleResponse matches = matchingService.getMatches(memberInfo.getMemberId());
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/week")
    public ResponseEntity<List<ScheduleResponse>> getThisWeekMatches(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<ScheduleResponse> matches = matchingService.getThisWeekMatches(memberInfo.getMemberId());
        return ResponseEntity.ok(matches);
    }
}
