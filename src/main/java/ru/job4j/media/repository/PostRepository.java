package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
