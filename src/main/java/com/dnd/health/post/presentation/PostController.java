package com.dnd.health.post.presentation;

import com.dnd.health.post.application.PostService;
import com.dnd.health.post.presentation.dto.PostRegisterRequest;
import com.dnd.health.post.presentation.dto.PostRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostRegisterResponse> register(@RequestBody PostRegisterRequest postRequest) {
        Long memberId = 1L;
        PostRegisterResponse postResponse = postService.save(postRequest.toCommand(memberId));
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }
}
