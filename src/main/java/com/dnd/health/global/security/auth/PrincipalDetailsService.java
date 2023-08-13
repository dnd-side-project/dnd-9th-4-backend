package com.dnd.health.global.security.auth;

import com.dnd.health.domain.member.domain.Member;
import com.dnd.health.domain.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository  memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("PrincipalDetailsService : 진입");
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()){
            Member useMember = member.get();
            return new PrincipalDetails(useMember);
        }
        // session.setAttribute("loginUser", user);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
