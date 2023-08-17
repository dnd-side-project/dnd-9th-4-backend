package com.dnd.health.domain.match.application;

import com.dnd.health.domain.match.application.dto.MatchingRefuseCommand;
import com.dnd.health.domain.match.domain.Match;
import com.dnd.health.domain.match.domain.MatchStatus;
import com.dnd.health.domain.match.exception.MatchNotFoundException;
import com.dnd.health.domain.match.presentation.dto.MatchApplicantResponse;
import com.dnd.health.domain.match.presentation.dto.MatchResponse;
import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.match.application.dto.MatchingApplyCommand;
import com.dnd.health.domain.match.domain.MatchingRepository;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.PostRepository;
import com.dnd.health.domain.post.domain.PostStatus;
import com.dnd.health.domain.post.exception.PostNotFoundException;
import com.dnd.health.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void apply(MatchingApplyCommand command) {
        Post post = postRepository.findById(command.getPostId())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Match match = Match.builder()
                .member(member)
                .post(post)
                .matchStatus(MatchStatus.APPLYING)
                .build();
        matchingRepository.save(match);
    }

    public MatchResponse getMatch(Long postId, Long memberId) {
        Match match = matchingRepository.findByPostIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new MatchNotFoundException(ErrorCode.MATCH_NOT_FOUND));
        return new MatchResponse(match);
    }

    public void delete(Long postId, Long memberId) {
        Match match = matchingRepository.findByPostIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new MatchNotFoundException(ErrorCode.MATCH_NOT_FOUND));

        matchingRepository.delete(match);
    }

    public void refuseMatch(MatchingRefuseCommand command) {
        matchingRepository.setMatchStatus(command.getPostId(), command.getApplicantId(), MatchStatus.REJECTED.name());
    }

    public void confirmMatch(MatchingRefuseCommand command) {
        matchingRepository.setMatchStatus(command.getPostId(), command.getApplicantId(), MatchStatus.MATCHED.name());
        postRepository.setPostStatusCompleted(PostStatus.COMPLETED.name(), command.getPostId());
    }

    public List<MatchApplicantResponse> getAllApplicant(Long postId) {
        List<Match> matches = matchingRepository.findAllByPostId(postId);
        return matches.stream()
                .map(MatchApplicantResponse::new)
                .collect(Collectors.toList());
    }

}
