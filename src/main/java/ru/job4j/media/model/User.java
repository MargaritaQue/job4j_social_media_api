package ru.job4j.media.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.media.security.Role;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Логин", requiredMode = Schema.RequiredMode.REQUIRED, example = "marga", minLength = 6, maxLength = 50)
    @NotBlank(message = "username не может быть пустым")
    @Length(min = 6,
            max = 50,
            message = "username должно быть не менее 6 и не более 50 символов")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Schema(description = "Email", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    @Email
    @NotBlank(message = "email не может быть пустым")
    @Length(min = 6,
            max = 100,
            message = "email должно быть не менее 6 и не более 100 символов")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Schema(description = "Пароль (хэш)", requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
    @NotBlank(message = "password не может быть пустым")
    @Length(min = 6,
            max = 255,
            message = "password должно быть не менее 6 и не более 255 символов")
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Schema(description = "Дата регистрации", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "persons_roles", joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
