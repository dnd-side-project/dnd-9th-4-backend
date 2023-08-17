package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.domain.Match;
import com.dnd.health.domain.match.domain.MatchStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchResponse {

    private Long id;

    private Long postId;

    private Long memberId;

    private MatchStatus status;

    public MatchResponse(Match match) {
        this.id = match.getId();
        this.postId = match.getPost().getId();
        this.memberId = match.getMember().getId();
        this.status = match.getMatchStatus();
    }
}
