package com.dnd.health.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common (공통적으로 사용)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "ERR_BAD_REQUEST", "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "ERR_METHOD_NOT_ALLOWED", "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR_INTERNAL_SERVER", "서버 내부 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "ERR_INVALID_TYPE", "유효하지 않은 데이터 유형입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ERR_ACCESS_DENIED", "접근이 거부되었습니다."),
    RESOURCE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ERR_UNAUTHORIZED", "인증이 필요합니다. 로그인을 해주세요."),
    RESOURCE_FORBIDDEN(HttpStatus.FORBIDDEN, "ERR_FORBIDDEN", "해당 리소스에 대한 권한이 없습니다."),

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_MEMBER_NOT_FOUND", "해당 UUID를 가진 멤버를 찾을 수 없습니다."),

    // JWT
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "ERR_EXPIRED_REFRESH_TOKEN", "만료된 리프레시 토큰입니다."),

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_POST_NOT_FOUND","해당 매칭 게시글을 찾을 수 없습니다."),

    // Match
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_MATCH_NOT_FOUND","해당 매칭을 찾을 수 없습니다."),
    MATCH_STATUS_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR_MATCH_STATUS_NOT_FOUND", "매칭 상태를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public int getStatus() {
        return this.status.value();
    }

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
