package ru.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.media.model.PostImage;

public interface PostImageRepository extends CrudRepository<PostImage, Long> {

    void deleteByPostId(Long postId);
}
