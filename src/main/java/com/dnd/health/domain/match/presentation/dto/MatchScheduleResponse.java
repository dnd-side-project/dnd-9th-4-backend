package com.dnd.health.domain.match.presentation.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MatchScheduleResponse {

    private List<ScheduleResponse> reservedSchedule;

    private List<ScheduleResponse> completedSchedule;

    public MatchScheduleResponse(List<ScheduleResponse> reservedSchedule, List<ScheduleResponse> completedSchedule) {
        this.reservedSchedule = reservedSchedule;
        this.completedSchedule = completedSchedule;
    }
}
