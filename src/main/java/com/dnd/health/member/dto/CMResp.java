package com.dnd.health.member.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CMResp<T> {
    private int code;  // 1(성공), -1(실패)
    private String message;
    private T data;
}
