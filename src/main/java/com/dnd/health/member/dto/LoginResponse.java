package com.dnd.health.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private String token;
    private String message;
    private String username;

    public LoginResponse(String token, String message, String username) {
        this.token = token;
        this.message = message;
        this.username = username;
    }
}
