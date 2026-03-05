package ru.job4j.media.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.media.model.Post;

import java.time.LocalDateTime;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId (Long userId);

    List<Post> findByCreatedAtBetween (LocalDateTime start, LocalDateTime end);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
