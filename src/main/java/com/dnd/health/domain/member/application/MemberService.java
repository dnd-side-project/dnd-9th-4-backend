package com.dnd.health.domain.member.application;

import static com.dnd.health.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import com.dnd.health.domain.member.domain.OAuth2Provider;
import com.dnd.health.domain.member.exception.MemberNotFoundException;
import com.dnd.health.domain.member.domain.Role;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveGuestMember() {
        return memberRepository.save(createGuestMember());
    }

    private Member createGuestMember() {
        return Member.builder()
                .username("NONE")
                .role(Role.ROLE_GUEST)
                .oAuth2Provider(OAuth2Provider.GUEST)
                .build();
    }

    public Optional<Member> getMemberByUserNumber(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member saveInfo(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberById(long id) {
        log.info("해당 id를 가진 멤버를 찾습니다.");
        return memberRepository.findById(id)
                .orElseThrow(()->new MemberNotFoundException(MEMBER_NOT_FOUND));
    }
}
