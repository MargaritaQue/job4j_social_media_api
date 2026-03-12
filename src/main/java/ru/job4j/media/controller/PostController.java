package ru.job4j.media.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.media.model.Post;
import ru.job4j.media.service.PostService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post,
                                       @RequestParam(required = false) List<String> urls) {
        postService.createPost(
                post.getUserId(),
                post.getTittle(),
                post.getContent(),
                urls
        );
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId,
                                       @RequestBody Post post,
                                       @RequestParam(required = false) List<String> urls) {
        postService.updatePost(
                post.getUserId(),
                postId,
                post.getTittle(),
                post.getContent(),
                urls
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId,
                                       @RequestParam Long userId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
