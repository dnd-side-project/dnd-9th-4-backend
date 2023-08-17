package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.application.dto.MatchingApplyCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchingApplyRequest {

    private Long postId;

    public MatchingApplyCommand toCommand(Long memberId){
        return new MatchingApplyCommand(postId, memberId);
    }
}
