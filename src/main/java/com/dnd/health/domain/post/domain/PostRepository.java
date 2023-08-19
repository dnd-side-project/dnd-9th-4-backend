package com.dnd.health.domain.post.domain;

import com.dnd.health.domain.profile.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByRegion(Region region);

    @Transactional
    @Modifying
    @Query( value = "update Post p set p.status = :status where p.post_id = :postId",
            nativeQuery = true)
    int setPostStatusCompleted(@PathParam("status") String status, Long postId);

    List<Post> findAllByWantedRuntimeAfter(LocalDateTime now);
}
