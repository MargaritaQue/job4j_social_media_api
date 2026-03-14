package ru.job4j.media.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "tittle не может быть пустым")
    @Length(min = 1,
            max = 255,
            message = "tittle должно быть от 1 до 255 символов")
    @Column(name = "tittle", nullable = false)
    private String tittle;

    @NotBlank(message = "content не может быть пустым")
    @Length(min = 1,
            max = 5000,
            message = "content должно быть от 1 до 5000 символов")
    @Column(name = "content_txt", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
