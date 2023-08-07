package com.dnd.health.config.auth;

import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login => 여기서 동작을 안한다. 필터 생성 필요 JwtAuthenticationFilter
@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository  memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("PrincipalDetailsService : 진입");
        Optional<Member> member = memberRepository.findByUsername(username);

        // session.setAttribute("loginUser", user);
        return new PrincipalDetails(member);
    }
}
