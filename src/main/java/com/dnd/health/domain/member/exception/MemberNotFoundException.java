package com.dnd.health.domain.member.exception;

import com.dnd.health.global.exception.BusinessException;
import com.dnd.health.global.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
