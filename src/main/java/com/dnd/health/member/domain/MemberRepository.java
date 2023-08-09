package com.dnd.health.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    Optional<Member> findById(Long id);

    Optional<Member> findMemberByRefreshToken(String refreshToken);
}
