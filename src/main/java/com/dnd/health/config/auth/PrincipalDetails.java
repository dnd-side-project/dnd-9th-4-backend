package com.dnd.health.config.auth;

import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final Member member;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member member) {
        this.member = member;
    }
    // OAuth 로그인 생성자
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    public Member getUser() {
        return getValidMember();
    }

    @Override
    public String getPassword() {
        Member validMember = getValidMember();
        String pw = validMember.getPassword().to();
        return pw != null ? pw : "";
    }

    @Override
    public String getUsername() {
        Member validMember = getValidMember();
        String username = validMember.getUsername().to();
        return username != null ? username : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (member != null) {
            Member roleMember = member;

            for (Role role : Role.values()) {
                if (roleMember.getRole() == role) {
                    authorities.add(new SimpleGrantedAuthority(role.name()));
                }
            }
        }
        return authorities;
    }

    private Member getValidMember() {
        if (member != null) {
            return member;
        }
        throw new IllegalStateException("Member is not valid");
    }

    @Override
    public String getName() {
        return null;
    }
}
