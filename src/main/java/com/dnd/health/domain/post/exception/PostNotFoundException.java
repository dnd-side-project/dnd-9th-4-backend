package com.dnd.health.domain.post.exception;

import com.dnd.health.global.exception.BusinessException;
import com.dnd.health.global.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
