package com.dnd.health.domain.profile.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ReviewResponse {

    private String reviewMessage;

    private int count;

    public ReviewResponse(Map.Entry<String, Integer> e) {
        this.reviewMessage = e.getKey();
        this.count = e.getValue();
    }
}
