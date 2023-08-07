package com.dnd.health.member.presentation;

import com.dnd.health.config.auth.PrincipalDetails;
import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.MemberRepository;
import com.dnd.health.member.domain.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 모든 사람이 접근 가능
    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.
    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @GetMapping("/member")
    public String user(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principal : "+principal.getUser().getId());
        System.out.println("principal : "+principal.getUser().getUsername());
        System.out.println("principal : "+principal.getUser().getPassword());
        System.out.println("principal : "+principal.getUser().getRole());
        return "user";
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("/manager/reports")
    public String reports() {
        return "reports";
    }

    // 어드민이 접근 가능
    @GetMapping("/admin/users")
    public List<Member> users(){
        return memberRepository.findAll();
    }

    @PostMapping("/join")
    public String join(@RequestBody Member member) {
        member.changePassword(bCryptPasswordEncoder.encode(member.getPassword().to()));
        member.changeRole(Role.ROLE_MEMBER);
        memberRepository.save(member);
        return "회원가입완료";
    }
}
