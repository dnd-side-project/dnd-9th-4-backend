package com.dnd.health.domain.post.presentation;

import com.dnd.health.domain.post.application.PostService;
import com.dnd.health.domain.post.presentation.dto.PostUpdateRequest;
import com.dnd.health.domain.post.presentation.dto.PostRegisterRequest;
import com.dnd.health.domain.post.presentation.dto.PostRegisterResponse;
import com.dnd.health.domain.post.presentation.dto.PostResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRegisterResponse> register(@RequestBody PostRegisterRequest postRequest) {
        long memberId = 1L;
        PostRegisterResponse postResponse = postService.save(postRequest.toCommand(memberId));
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        List<PostResponse> postResponses = postService.findAll();
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/simple")
    public ResponseEntity<List<PostResponse>> getSomePost() {
        Long memberId = 1L;
        List<PostResponse> postResponses = postService.findSomePost(memberId);
        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postRequest) {
        PostResponse postResponse = postService.update(postRequest.toCommand(postId));
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
