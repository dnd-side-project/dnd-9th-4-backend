package com.dnd.health.domain.profile.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.profile.application.dto.ReviewCommand;
import com.dnd.health.domain.profile.domain.Review;
import com.dnd.health.domain.profile.domain.ReviewRepository;
import com.dnd.health.domain.profile.presentation.dto.ReviewResponse;
import com.dnd.health.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public void review(ReviewCommand command) {
        Member rater = memberRepository.findById(command.getRaterId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Member target = memberRepository.findById(command.getTargetId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        reviewRepository.save(command.toDomain(rater, target));
    }

    public List<ReviewResponse> getReviews(Long memberId) {
        List<Review> reviews = reviewRepository.findAllByTargetId(memberId);

        // 리뷰 데이터 가공 -> 같은 리뷰에 대해 카운팅
        Map<String, Integer> overallReview = new HashMap<>();
        reviews.stream().forEach((review) -> {
            review.getReviewMessages().forEach((msg) -> {
                if(overallReview.get(msg.getValue()) != null) {
                    overallReview.replace(msg.getValue(), overallReview.get(msg.getValue())+1);
                } else {
                    overallReview.put(msg.getValue(), 1);
                }
            });
        });

        // 내림차순 정렬 후 상위 4개 반환
        List<Map.Entry<String, Integer>> list = new LinkedList<>(overallReview.entrySet());
        list.sort(Map.Entry.comparingByValue(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }));

        return list.stream().map(ReviewResponse::new).collect(Collectors.toList());
    }
}
