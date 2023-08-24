package com.dnd.health.domain.member.dto.response;

import com.dnd.health.domain.member.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemberSimpleInfoResponse {

    @ApiModelProperty("member의 고유 Kakao Id")
    private final String kakaoId;

    @ApiModelProperty("member의 고유 Id")
    private final long memberId;

    public MemberSimpleInfoResponse(Member member) {
        this.kakaoId = member.getProviderId();
        this.memberId = member.getId();
    }
}
