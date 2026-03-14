package ru.job4j.media.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Идентификатор поста", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Идентификатор автора", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @Schema(description = "Заголовок", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255)
    @NotBlank(message = "tittle не может быть пустым")
    @Length(min = 1,
            max = 255,
            message = "tittle должно быть от 1 до 255 символов")
    @Column(name = "tittle", nullable = false)
    private String tittle;

    @Schema(description = "Текст поста", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 5000)
    @NotBlank(message = "content не может быть пустым")
    @Length(min = 1,
            max = 5000,
            message = "content должно быть от 1 до 5000 символов")
    @Column(name = "content_txt", nullable = false)
    private String content;

    @Schema(description = "Дата создания", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "Дата обновления", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
