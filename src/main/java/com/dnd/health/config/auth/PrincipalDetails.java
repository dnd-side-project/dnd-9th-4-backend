package com.dnd.health.config.auth;

import com.dnd.health.member.domain.Member;
import com.dnd.health.member.domain.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class PrincipalDetails implements UserDetails {

    private Optional<Member> member;

    public PrincipalDetails(Optional<Member> member) {
        this.member = member;
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

        if (member.isPresent()) {
            Member roleMember = member.get();

            for (Role role : Role.values()) {
                if (roleMember.getRole() == role) {
                    authorities.add(new SimpleGrantedAuthority(role.name()));
                }
            }
        }
        return authorities;
    }

    private Member getValidMember() {
        if (member.isPresent()) {
            return member.get();
        }
        throw new IllegalStateException("Member is not valid");
    }
}
