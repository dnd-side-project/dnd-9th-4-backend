package com.dnd.health.domain.match.exception;

import com.dnd.health.global.exception.BusinessException;
import com.dnd.health.global.exception.ErrorCode;

public class MatchNotFoundException extends BusinessException {
    public MatchNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
