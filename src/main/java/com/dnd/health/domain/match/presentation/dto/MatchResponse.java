package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.domain.Match;
import com.dnd.health.domain.match.domain.MatchStatus;
import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.post.domain.Post;
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

    public MatchResponse(Match match, long postId, long memberId) {
        this.id = match.getId();
        this.postId = postId;
        this.memberId = memberId;
        this.status = match.getMatchStatus();
    }
}
