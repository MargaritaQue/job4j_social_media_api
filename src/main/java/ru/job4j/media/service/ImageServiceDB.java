package ru.job4j.media.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.media.model.PostImage;
import ru.job4j.media.repository.PostImageRepository;

@AllArgsConstructor
@Service
public class ImageServiceDB implements ImageService {
    private final PostImageRepository postImageRepository;

    @Override
    public void createImgForPost(Long postId, String url) {
        PostImage postImage = new PostImage(null, postId, url);
        postImageRepository.save(postImage);
    }

    @Override
    public void deleteAllImgByPostId(Long postId) {
        postImageRepository.deleteByPostId(postId);
    }
}
