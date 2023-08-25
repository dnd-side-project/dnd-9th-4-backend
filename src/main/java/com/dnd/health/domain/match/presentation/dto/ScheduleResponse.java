package com.dnd.health.domain.match.presentation.dto;

import com.dnd.health.domain.match.domain.Match;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ScheduleResponse {

    private Long id;

    private Long postId;

    private Long memberId;

    private String profileImg;

    private String username;

    private String title;

    private String region;

    private String sport;

    private String runtime;

    public ScheduleResponse(Match match, boolean isWriter) {
        this.id = match.getId();
        this.postId = match.getPost().getId();
        if(isWriter) {
            this.memberId = match.getMember().getId();
            this.profileImg = match.getMember().getProfile().getProfileImg();
            this.username = match.getMember().getUsername().to();
        }else {
            this.memberId = match.getPost().getMember().getId();
            this.profileImg = match.getPost().getMember().getProfile().getProfileImg();
            this.username = match.getPost().getMember().getUsername().to();
        }
        this.title = match.getPost().getTitle().to();
        this.region = match.getPost().getRegion().to();
        this.sport = match.getPost().getSport().name();
        this.runtime = match.getPost().getWanted().getRuntime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
