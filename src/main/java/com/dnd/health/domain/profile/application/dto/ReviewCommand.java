package com.dnd.health.domain.profile.application.dto;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.profile.domain.Review;
import com.dnd.health.domain.profile.domain.ReviewMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ReviewCommand {

    private Long raterId;

    private Long targetId;

    private int score;

    private List<String> reviews;

    public Review toDomain(Member rater, Member target) {
        return Review.builder()
                .rater(rater)
                .target(target)
                .score(score)
                .reviewMessages(reviews.stream().map(ReviewMessage::new).collect(Collectors.toList()))
                .build();
    }
}
