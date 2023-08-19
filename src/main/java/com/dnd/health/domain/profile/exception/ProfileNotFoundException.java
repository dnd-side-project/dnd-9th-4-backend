package com.dnd.health.domain.profile.exception;

import com.dnd.health.global.exception.BusinessException;
import com.dnd.health.global.exception.ErrorCode;

public class ProfileNotFoundException extends BusinessException {

    public ProfileNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
