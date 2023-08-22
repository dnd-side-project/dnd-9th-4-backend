package com.dnd.health.domain.profile.presentation;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.profile.application.ReviewService;
import com.dnd.health.domain.profile.presentation.dto.ReviewQueryRequest;
import com.dnd.health.domain.profile.presentation.dto.ReviewRequest;
import com.dnd.health.domain.profile.presentation.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final MemberInfoService memberInfoService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> review(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody ReviewRequest reviewRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        reviewService.review(reviewRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<ReviewResponse> reviewResponse = reviewService.getReviews(memberInfo.getMemberId());
        return ResponseEntity.ok(reviewResponse);
    }
}
