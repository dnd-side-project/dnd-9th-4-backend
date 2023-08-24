package com.dnd.health.domain.post.presentation;

import static com.dnd.health.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.post.application.PostService;
import com.dnd.health.domain.post.presentation.dto.*;

import com.dnd.health.domain.profile.presentation.dto.RecruitedPostResponse;
import com.dnd.health.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final MemberInfoService memberInfoService;
    private final MemberRepository memberRepository;
    private final PostService postService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostRegisterResponse> register(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody PostRegisterRequest postRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        PostRegisterResponse postResponse = postService.save(postRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostResponse>> getAllPost(@AuthenticationPrincipal SessionUser sessionUser) {
        String id = sessionUser.getId();
        Optional<Member> isMember = memberRepository.findByProviderId(id);
        Member member = isMember.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        List<PostResponse> postResponses = postService.findAll(member);
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/simple")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PostResponse>> getSomePost(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<PostResponse> postResponses = postService.findSomePost(memberInfo.getMemberId());
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<WrittenPostResponse>> getMyPosts(@AuthenticationPrincipal SessionUser sessionUser) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<WrittenPostResponse> postResponses = postService.findMyPosts(memberInfo.getMemberId());
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponse> getPost(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostResponse> updatePost(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId, @RequestBody PostUpdateRequest postRequest) {
        PostResponse postResponse = postService.update(postRequest.toCommand(postId));
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
