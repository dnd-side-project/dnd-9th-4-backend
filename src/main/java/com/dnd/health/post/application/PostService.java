package com.dnd.health.post.application;

import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.MemberRepository;
import com.dnd.health.post.application.dto.PostRegisterCommand;
import com.dnd.health.post.domain.Post;
import com.dnd.health.post.domain.PostRepository;
import com.dnd.health.post.presentation.dto.PostRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostRegisterResponse save(PostRegisterCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        Post post = command.toDomain(member);
        Post saved = postRepository.save(post);
        return new PostRegisterResponse(saved.getId());
    }

}
