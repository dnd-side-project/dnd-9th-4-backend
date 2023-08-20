package com.dnd.health.domain.match.application;

import com.dnd.health.domain.match.application.dto.MatchingRefuseCommand;
import com.dnd.health.domain.match.domain.Match;
import com.dnd.health.domain.match.domain.MatchStatus;
import com.dnd.health.domain.match.exception.MatchNotFoundException;
import com.dnd.health.domain.match.presentation.dto.MatchApplicantResponse;
import com.dnd.health.domain.match.presentation.dto.MatchResponse;
import com.dnd.health.domain.match.presentation.dto.MatchScheduleResponse;
import com.dnd.health.domain.match.presentation.dto.ScheduleResponse;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public void confirmMatch(MatchingRefuseCommand command) {
        matchingRepository.setMatchStatus(command.getPostId(), command.getApplicantId(), MatchStatus.MATCHED.name());
        postRepository.setPostStatusCompleted(PostStatus.COMPLETED.name(), command.getPostId());
        postRepository.setMatchedMemberId(command.getApplicantId(), command.getPostId());
    }

    public List<MatchApplicantResponse> getAllApplicant(Long postId) {
        List<Match> matches = matchingRepository.findAllByPostId(postId);
        return matches.stream()
                .map(MatchApplicantResponse::new)
                .collect(Collectors.toList());
    }

    public MatchScheduleResponse getMatches(Long memberId) {
        List<ScheduleResponse> reserved = new ArrayList<>();
        List<ScheduleResponse> completed = new ArrayList<>();

        LocalDateTime today = LocalDateTime.now();
        // 신청한 매칭
        List<Match> applyList = matchingRepository.findByMemberId(memberId);
        applyList.stream().forEach((match)-> {
            if(match.getMatchStatus() == MatchStatus.MATCHED){
                if(match.getPost().getWanted().getRuntime().compareTo(today) >= 0) {
                    reserved.add(new ScheduleResponse(match, false));
                } else {
                    completed.add(new ScheduleResponse(match, false));
                }
            }
        });

        // 작성한 매칭
        List<Post> writtenList = postRepository.findAllByMemberId(memberId);
        writtenList.stream().forEach((post)-> {
            if(post.getStatus() == PostStatus.COMPLETED) {
                Match match = matchingRepository.findByPostIdAndMemberId(post.getId(), post.getMatchedMemberId())
                        .orElseThrow(() -> new MatchNotFoundException(ErrorCode.MATCH_STATUS_NOT_FOUND));
                if(match.getPost().getWanted().getRuntime().compareTo(today) >= 0) {
                    reserved.add(new ScheduleResponse(match, true));
                } else {
                    completed.add(new ScheduleResponse(match, true));
                }
            }
        });
        return new MatchScheduleResponse(reserved, completed);
    }
}
