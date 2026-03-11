package ru.job4j.media.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.media.model.Post;
import ru.job4j.media.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostServiceDB implements PostService {
    private final ImageService imageService;
    private final PostRepository postRepository;

    @Override
    public void createPost(Long userId, String tittle, String content, List<String> urls) {
        Post post = new Post(null, userId, tittle, content, LocalDateTime.now(), LocalDateTime.now());
        postRepository.save(post);
        if (urls != null) {
            urls.forEach(url -> imageService.createImgForPost(post.getId(), url));
        }
    }

    @Override
    public void updatePost(Long userId, Long postId, String tittle, String content, List<String> urls) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User cannot modify this post");
        }
        post.setTittle(tittle);
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
        imageService.deleteAllImgByPostId(postId);
        if (urls != null) {
            urls.forEach(url -> imageService.createImgForPost(post.getId(), url));
        }
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional
                .orElseThrow(() -> new NoSuchElementException("Post not found"));
        if (!post.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User cannot modify this post");
        }
        imageService.deleteAllImgByPostId(postId);
        postRepository.delete(post);
    }
}
