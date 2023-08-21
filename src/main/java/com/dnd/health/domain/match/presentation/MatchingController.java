package com.dnd.health.domain.match.presentation;

import com.dnd.health.domain.match.application.MatchingService;
import com.dnd.health.domain.match.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping("/apply")
    public ResponseEntity<Void> apply(@RequestBody MatchingApplyRequest matchingRequest){
        matchingService.apply(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/refuse")
    public ResponseEntity<Void> refuseMatch(@RequestBody MatchingRefuseRequest matchingRequest) {
        matchingService.refuseMatch(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmMatch(@RequestBody MatchingRefuseRequest matchingRequest) {
        matchingService.confirmMatch(matchingRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<MatchApplicantResponse>> getAllApplicant(@PathVariable Long postId) {
        List<MatchApplicantResponse> applicants = matchingService.getAllApplicant(postId);
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<MatchResponse> getMatchStatus(@PathVariable Long postId, @RequestBody MatchQueryRequest matchRequest) {
        MatchResponse match = matchingService.getMatch(postId, matchRequest.getMemberId());
        return ResponseEntity.ok(match);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> cancelMatch(@PathVariable Long postId, @RequestBody MatchQueryRequest matchRequest) {
        matchingService.delete(postId, matchRequest.getMemberId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<MatchScheduleResponse> getMatches(@RequestBody MatchQueryRequest matchRequest) {
        MatchScheduleResponse matches = matchingService.getMatches(matchRequest.getMemberId());
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/week")
    public ResponseEntity<List<ScheduleResponse>> getThisWeekMatches(@RequestBody MatchQueryRequest matchRequest) {
        List<ScheduleResponse> matches = matchingService.getThisWeekMatches(matchRequest.getMemberId());
        return ResponseEntity.ok(matches);
    }
}
