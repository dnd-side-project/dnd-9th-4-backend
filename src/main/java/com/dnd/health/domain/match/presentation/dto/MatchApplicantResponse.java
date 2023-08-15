package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.domain.Match;
import com.dnd.health.domain.match.domain.MatchStatus;
import com.dnd.health.domain.member.domain.Age;
import com.dnd.health.domain.member.domain.Gender;
import com.dnd.health.domain.member.domain.Username;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchApplicantResponse {

    private Long memberId;

    private String username;

    private String gender;

    private String age;

    private MatchStatus matchStatus;

    public MatchApplicantResponse(Match match) {
        this.memberId = match.getMember().getId();
        this.username = match.getMember().getUsername().to();
        this.gender = match.getMember().getGender().to();
        this.age = match.getMember().getAge().to();
        this.matchStatus = match.getMatchStatus();
    }
}
