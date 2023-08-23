package com.dnd.health.domain.post.presentation;

import com.dnd.health.domain.jwt.dto.SessionUser;
import com.dnd.health.domain.member.application.MemberInfoService;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import com.dnd.health.domain.post.application.PostService;
import com.dnd.health.domain.post.presentation.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final MemberInfoService memberInfoService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRegisterResponse> register(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody PostRegisterRequest postRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        PostRegisterResponse postResponse = postService.save(postRequest.toCommand(memberInfo.getMemberId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(@AuthenticationPrincipal SessionUser sessionUser) {
        List<PostResponse> postResponses = postService.findAll();
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/simple")
    public ResponseEntity<List<PostResponse>> getSomePost(@AuthenticationPrincipal SessionUser sessionUser, @RequestBody PostQueryRequest postRequest) {
        MemberInfoResponse memberInfo = memberInfoService.getMember(sessionUser.getId());
        List<PostResponse> postResponses = postService.findSomePost(memberInfo.getMemberId());
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long postId, @RequestBody PostUpdateRequest postRequest) {
        PostResponse postResponse = postService.update(postRequest.toCommand(postId));
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
