package com.dnd.health.domain.match.exception;

import com.dnd.health.global.exception.BusinessException;
import com.dnd.health.global.exception.ErrorCode;

public class MatchStatusNotFoundException extends BusinessException {
    public MatchStatusNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
