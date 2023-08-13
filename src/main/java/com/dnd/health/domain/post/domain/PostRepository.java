package com.dnd.health.domain.post.domain;

import com.dnd.health.domain.profile.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByRegion(Region region);
}
