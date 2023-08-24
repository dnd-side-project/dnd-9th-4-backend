package com.dnd.health.domain.member.application;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberService memberService;

    public MemberInfoResponse getMember(String id) {
        Member member = memberService.getMemberById(id);
        return new MemberInfoResponse(member);
    }

    public Member getNewPleMember(long id) {
        return memberService.getMemberById(id);
    }
}