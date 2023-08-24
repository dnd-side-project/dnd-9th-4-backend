package com.dnd.health.domain.post.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.domain.Role;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.post.application.dto.PostRegisterCommand;
import com.dnd.health.domain.post.application.dto.PostUpdateCommand;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.PostRepository;
import com.dnd.health.domain.post.exception.PostNotFoundException;
import com.dnd.health.domain.post.presentation.dto.PostRegisterResponse;
import com.dnd.health.domain.post.presentation.dto.PostResponse;
import com.dnd.health.domain.post.presentation.dto.WrittenPostResponse;
import com.dnd.health.domain.profile.presentation.dto.RecruitedPostResponse;
import com.dnd.health.global.exception.ErrorCode;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostRegisterResponse save(PostRegisterCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        System.out.println("member = " + member.getUsername().to());
        Post post = command.toDomain(member);
        Post saved = postRepository.save(post);
        return new PostRegisterResponse(saved.getId());
    }

    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        return new PostResponse(post);
    }

//    public List<PostResponse> findAll() {
//        return postRepository.findAll().stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());
//    }

    public List<PostResponse> findAll(long id) {
        return postRepository.findAllByMember_RoleAndMember_Id(Role.ROLE_MEMBER, id).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public PostResponse update(PostUpdateCommand command) {
        Post post = postRepository.findById(command.getId())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        Post updatedPost = postRepository.save(command.toDomain(post.getMember()));
        return new PostResponse(updatedPost);
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        postRepository.delete(post);
    }

    public List<PostResponse> findSomePost(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<Post> posts = postRepository.findAllByRegion(member.getProfile().getRegion());
        if(posts.size() >= 4) posts = posts.subList(0, 3);
        return posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    public List<WrittenPostResponse> findMyPosts(long memberId) {
        List<Post> posts = postRepository.findAllByMemberId(memberId);
        return posts.stream().map(WrittenPostResponse::new).collect(Collectors.toList());
    }
}
