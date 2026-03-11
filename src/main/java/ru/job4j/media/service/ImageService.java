package ru.job4j.media.service;

import org.springframework.transaction.annotation.Transactional;

public interface ImageService {

    @Transactional
    void createImgForPost(Long postId, String url);

    @Transactional
    void deleteAllImgByPostId(Long postId);
}
