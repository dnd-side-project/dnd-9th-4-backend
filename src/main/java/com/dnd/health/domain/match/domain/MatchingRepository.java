package com.dnd.health.domain.match.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Match, Long> {

    Optional<Match> findByPostIdAndMemberId(Long postId, Long memberId);

    List<Match> findAllByPostId(Long postId);

    @Transactional
    @Modifying
    @Query( value = "update Match m set m.match_status = :status where m.application_post_id = :postId and m.applicant_id = :applicantId",
            nativeQuery = true)
    void setMatchStatus(@Param("postId") Long postId, @Param("applicantId") Long applicantId, @Param("status") String status);
}
