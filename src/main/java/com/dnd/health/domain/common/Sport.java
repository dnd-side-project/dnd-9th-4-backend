package com.dnd.health.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sport {
    FITNESS,
    RUNNING,
    CYCLE,
    HIKING,
    SWIM;

    @JsonCreator
    public static Sport from(String value) {
        for(Sport sport : Sport.values()) {
            if(sport.name().equals(value)) {
                return sport;
            }
        }
        return null;
    }
}
