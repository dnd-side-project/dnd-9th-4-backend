package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.application.dto.MatchingRefuseCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchingRefuseRequest {

    private Long postId;

    private Long applicantId;

    public MatchingRefuseCommand toCommand() {
        return new MatchingRefuseCommand(postId, applicantId);
    }
}
