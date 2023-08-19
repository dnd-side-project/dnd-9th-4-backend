package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.domain.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleResponse {

    private Long id;

    private Long memberId;

    private String profileImg;

    private String title;

    private String region;

    private String sport;

    private String runtime;

    public ScheduleResponse(Match match, boolean isWriter) {
        this.id = match.getId();
        if(isWriter) {
            this.memberId = match.getMember().getId();
            this.profileImg = match.getMember().getProfile().getProfileImg();
        }else {
            this.memberId = match.getPost().getMember().getId();
            this.profileImg = match.getPost().getMember().getProfile().getProfileImg();
        }
        this.title = match.getPost().getTitle().to();
        this.region = match.getPost().getRegion().to();
        this.sport = match.getPost().getSport().name();
        this.runtime = match.getPost().getWanted().getRuntime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
