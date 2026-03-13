package ru.job4j.media.service;

import org.springframework.transaction.annotation.Transactional;
import ru.job4j.media.model.Post;

import java.util.List;

public interface PostService {

    @Transactional
    Post createPost(Long userId, String tittle, String content, List<String> url);

    @Transactional
    void updatePost(Long userId, Long postId, String tittle, String content, List<String> url);

    @Transactional
    void deletePost(Long userId, Long postId);
}
