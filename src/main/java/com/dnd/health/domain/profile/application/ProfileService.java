package com.dnd.health.domain.profile.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.post.domain.Post;
import com.dnd.health.domain.post.domain.PostRepository;
import com.dnd.health.domain.profile.application.dto.ProfileRegisterCommand;
import com.dnd.health.domain.profile.application.dto.ProfileUpdateCommand;
import com.dnd.health.domain.profile.domain.Profile;
import com.dnd.health.domain.profile.domain.ProfileRepository;
import com.dnd.health.domain.profile.domain.Review;
import com.dnd.health.domain.profile.domain.ReviewRepository;
import com.dnd.health.domain.profile.exception.ProfileNotFoundException;
import com.dnd.health.domain.profile.presentation.dto.MateResponse;
import com.dnd.health.domain.profile.presentation.dto.ProfileResponse;
import com.dnd.health.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(ProfileRegisterCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        member.updateInfo(command.getProfileImg(), command.getUsername(), command.getGender());
        memberRepository.save(member);

        Profile profile = command.toDomain(member);
        return profileRepository.save(profile).getId();
    }

    @Transactional
    public ProfileResponse getProfile(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
        List<Review> reviews = reviewRepository.findAllByTargetId(memberId);

        // 새로운 리뷰 반영한 GPA 계산 및 세팅
        String gpa = calculateGpa(reviews);
        profile.setGpa(gpa);
        profileRepository.save(profile);

        // 모집중인 게시글 찾기
        List<Post> posts = postRepository.findAllByWantedRuntimeAfter(LocalDateTime.now());

        return new ProfileResponse(profile, gpa, getTop4Reviews(reviews), posts);
    }

    private String calculateGpa(List<Review> reviews) {
        double gpa = reviews.stream().mapToInt(Review::getScore).sum() / (double)reviews.size();
        return Double.toString(gpa);
    }

    private List<Map.Entry<String, Integer>> getTop4Reviews(List<Review> reviews) {
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

        for(Map.Entry<String, Integer> entry : list){
            System.out.println("key : " + entry.getKey() + ", value : " + entry.getValue());
        }
        if(list.size() > 4) {
            list = list.subList(0, 4);
        }
        return list;
    }

    @Transactional
    public void update(ProfileUpdateCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        member.updateInfo(command.getProfileImg(), command.getUsername(), command.getGender());
        memberRepository.save(member);

        Profile profile = profileRepository.findByMemberId(command.getMemberId())
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
        profile.updateInfo(command);
        profileRepository.save(profile);
    }

    public List<MateResponse> getMatesAround(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ProfileNotFoundException(ErrorCode.PROFILE_NOT_FOUND));
        List<Profile> matesAround = profileRepository.findAllByRegionAndMemberIdNot(profile.getRegion(), memberId);
        if(matesAround.size() > 20) matesAround = matesAround.subList(0, 20);
        return matesAround.stream().map(MateResponse::new).collect(Collectors.toList());
    }
}
