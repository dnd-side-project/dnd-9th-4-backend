package com.dnd.health.domain.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(long username);

    Optional<Member> findById(Long id);

    @Query("SELECT m FROM Member m WHERE m.providerId = :id")
    Optional<Member> findByKakaoId(@Param("id") ProviderId providerId);
}
