package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.Post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }

    @Test
    public void whenSavePost_thenFindById() {
        var post = new Post();
        post.setUserId(1L);
        post.setTittle("Tittle");
        post.setContent("CONTENT");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        var foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTittle()).isEqualTo("Tittle");
    }

    @Test
    public void whenFindAll_thenReturnAllPosts() {
        var post = new Post();
        post.setUserId(1L);
        post.setTittle("Tittle");
        post.setContent("CONTENT");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        var post1 = new Post();
        post1.setUserId(1L);
        post1.setTittle("Tittle1");
        post1.setContent("CONTENT1");
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        postRepository.save(post1);
        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getTittle).contains("Tittle", "Tittle1");
    }

}