package com.dnd.health.domain.profile.presentation;

import com.dnd.health.domain.profile.application.ReviewService;
import com.dnd.health.domain.profile.presentation.dto.ReviewQueryRequest;
import com.dnd.health.domain.profile.presentation.dto.ReviewRequest;
import com.dnd.health.domain.profile.presentation.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> review(@RequestBody ReviewRequest reviewRequest) {
        reviewService.review(reviewRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(@RequestBody ReviewQueryRequest request) {
        List<ReviewResponse> reviewResponse = reviewService.getReviews(request.getMemberId());
        return ResponseEntity.ok(reviewResponse);
    }
}
