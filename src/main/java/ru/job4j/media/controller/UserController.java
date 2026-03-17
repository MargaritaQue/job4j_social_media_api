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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import ru.job4j.media.model.User;
import ru.job4j.media.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "UserController", description = "UserController management APIs")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Operation(summary = "Создать пользователя", description = "Регистрация нового пользователя. createdAt задаётся на сервере.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь создан",
                    content = @Content(schema = @Schema(implementation = User.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Невалидные данные или нарушение уникальности")
    })
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(user);
    }

    @Operation(summary = "Список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список пользователей",
            content = @Content(schema = @Schema(implementation = User[].class)))
    @GetMapping
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Operation(summary = "Получить пользователя по id", description = "Возвращает пользователя по userId.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Найден",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить пользователя по id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Удалён"),
            @ApiResponse(responseCode = "404", description = "Не найден")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
