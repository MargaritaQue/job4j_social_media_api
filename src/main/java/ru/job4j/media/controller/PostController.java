package ru.job4j.media.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import ru.job4j.media.dto.UserDTO;
import ru.job4j.media.model.Post;
import ru.job4j.media.model.User;
import ru.job4j.media.repository.PostRepository;
import ru.job4j.media.repository.UserRepository;
import ru.job4j.media.service.PostService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post,
                                       @RequestParam(required = false) List<String> urls) {
        Post created = postService.createPost(
                post.getUserId(),
                post.getTittle(),
                post.getContent(),
                urls
        );
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(created);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable Long postId,
                                       @Valid @RequestBody Post post,
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

    @GetMapping("/by-users")
    public List<UserDTO> getUsersWithPosts(@RequestParam List<Long> userId) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Long id : userId) {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                continue;
            }
            List<Post> posts = postRepository.findByUserId(id);
            userDTOS.add(new UserDTO(user.getId(), user.getUsername(), posts));
        }
        return userDTOS;
    }
}
