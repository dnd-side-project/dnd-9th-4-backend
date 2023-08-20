package com.dnd.health.domain.profile.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(Long memberId);

    List<Profile> findAllByRegionAndMemberIdNot(Region region, Long memberId);
}
