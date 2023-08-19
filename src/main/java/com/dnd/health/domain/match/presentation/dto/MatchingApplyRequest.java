package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.application.dto.MatchingApplyCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchingApplyRequest {

    private Long postId;

    private Long memberId;

    public MatchingApplyCommand toCommand(){
        return new MatchingApplyCommand(postId, memberId);
    }
}
