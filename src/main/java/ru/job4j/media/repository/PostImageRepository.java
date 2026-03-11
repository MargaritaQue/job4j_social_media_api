package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.PostImage;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {

    void deleteByPostId(Long postId);
}
