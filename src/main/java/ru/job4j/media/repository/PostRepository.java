package ru.job4j.media.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.media.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post post SET post.tittle = :tittle, post.content = :content
            WHERE post.id = :id
            """)
    int postUpdateTittleAndContent(@Param("id") Long id, @Param("tittle") String tittle, @Param("content") String content);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM PostImage pi WHERE pi.id = :imageId
            """)
    int postDeletePostImage(@Param("imageId") Long imageId);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM Post post WHERE post.id = :postId
            """)
    int postDelete(@Param("postId") Long postId);

    @Query(value = """
            SELECT post FROM Post post WHERE post.userId IN
            (SELECT s.userFollowed FROM Subscription s WHERE s.userFollower = :userId) ORDER BY post.createdAt DESC
            """,
            countQuery = """
                    SELECT COUNT(post) FROM Post post WHERE post.userId IN
                    (SELECT s.userFollowed FROM Subscription s WHERE s.userFollower = :userId)
                    """
    )
    Page<Post> userPosts(@Param("userId") Long userId, Pageable pageable);
}
