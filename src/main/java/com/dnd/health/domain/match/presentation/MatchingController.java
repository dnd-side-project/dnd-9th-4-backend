package com.dnd.health.domain.match.presentation;

import com.dnd.health.domain.match.application.MatchingService;
import com.dnd.health.domain.match.presentation.dto.MatchApplicantResponse;
import com.dnd.health.domain.match.presentation.dto.MatchResponse;
import com.dnd.health.domain.match.presentation.dto.MatchingApplyRequest;
import com.dnd.health.domain.match.presentation.dto.MatchingRefuseRequest;
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
        Long memberId = 2L;
        matchingService.apply(matchingRequest.toCommand(memberId));
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
    public ResponseEntity<MatchResponse> getMatchStatus(@PathVariable Long postId) {
        Long memberId = 2L;
        MatchResponse match = matchingService.getMatch(postId, memberId);
        return ResponseEntity.ok(match);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> cancelMatch(@PathVariable Long postId) {
        Long memberId = 2L;
        matchingService.delete(postId, memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
