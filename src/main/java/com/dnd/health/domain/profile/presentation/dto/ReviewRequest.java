package com.dnd.health.domain.profile.presentation.dto;

import com.dnd.health.domain.profile.application.dto.ReviewCommand;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewRequest {

    private Long targetId;

    private int score;

    private List<String> reviews;

    public ReviewCommand toCommand(Long raterId) {
        return new ReviewCommand(raterId, targetId, score, reviews);
    }
}
