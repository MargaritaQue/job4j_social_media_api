package ru.job4j.media.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "PostController", description = "PostController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Operation(summary = "Создать пост", description = "Принимает тело поста и urls изображений.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пост создан", content = @Content(schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
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

    @Operation(summary = "Обновить пост по id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Обновлён"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
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

    @Operation(summary = "Удалить пост по id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Удалён"),
            @ApiResponse(responseCode = "404", description = "Не найден")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId,
                                       @RequestParam Long userId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Посты пользователей по списку id", description = "Возвращает для каждого userId объект UserDTO: userId, username, posts.")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO[].class)))
    @GetMapping("/by-users")
    public List<UserDTO> getUsersWithPosts(@RequestParam List<Long> userId) {
        List<UserDTO> userDTOS = new ArrayList<>();
        Iterable<User> users = userRepository.findAllById(userId);
        List<Long> ids = new ArrayList<>();
        for (User u : users) {
            ids.add(u.getId());
        }
        List<Post> allPosts = postRepository.findByUserIdIn(ids);
        Map<Long, List<Post>> postsByUserId = new HashMap<>();
        for (Post p : allPosts) {
            postsByUserId
                    .computeIfAbsent(p.getUserId(), k -> new ArrayList<>())
                    .add(p);
        }
        for (User u : users) {
            List<Post> posts = postsByUserId.getOrDefault(u.getId(), List.of());
            userDTOS.add(new UserDTO(u.getId(), u.getUsername(), posts));
        }
        return userDTOS;
    }
}
