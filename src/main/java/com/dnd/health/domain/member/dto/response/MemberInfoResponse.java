package com.dnd.health.domain.member.dto.response;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.OAuth2Provider;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    @ApiModelProperty("member의 고유 Id")
    private final long memberId;

    @ApiModelProperty("member의 이름")
    private final String memberName;

    @ApiModelProperty("member의 oauth 제공자(GUEST, KAKAO)")
    private final OAuth2Provider oauth2Provider;

    public MemberInfoResponse(Member member) {
        this.memberId = member.getId();
        this.memberName =
                member.getUsername().to();
        this.oauth2Provider = member.getOauth2Provider();
    }

}
