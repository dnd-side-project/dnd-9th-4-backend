package com.dnd.health.domain.jwt.handler.exception;

import com.dnd.health.global.exception.ErrorCode;

public class JwtException extends RuntimeException{
    private final ErrorCode errorCode;

    public JwtException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
