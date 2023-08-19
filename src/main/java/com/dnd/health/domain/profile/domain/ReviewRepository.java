package com.dnd.health.domain.profile.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByTargetId(Long memberId);
}
