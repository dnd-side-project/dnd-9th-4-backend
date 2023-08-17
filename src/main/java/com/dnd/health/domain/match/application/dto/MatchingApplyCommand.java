package com.dnd.health.domain.match.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchingApplyCommand {

    private Long postId;

    private Long memberId;
}
