package com.dnd.health.domain.match.domain;

import com.dnd.health.domain.match.exception.MatchStatusNotFoundException;
import com.dnd.health.global.exception.ErrorCode;

public enum MatchStatus {

    APPLYING(Values.APPLYING),
    MATCHED(Values.MATCHED),
    REJECTED(Values.REJECTED);

    MatchStatus(String val) {
        if(!this.name().equals(val))
            throw new MatchStatusNotFoundException(ErrorCode.MATCH_STATUS_NOT_FOUND);
    }

    public static class Values {
        public static final String NOT_APPLY = "NOT_APPLY";
        public static final String APPLYING = "APPLYING";
        public static final String MATCHED = "MATCHED";
        public static final String REJECTED = "REJECTED";
    }

}
