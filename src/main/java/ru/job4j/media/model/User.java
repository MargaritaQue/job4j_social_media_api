package ru.job4j.media.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username не может быть пустым")
    @Length(min = 6,
            max = 50,
            message = "username должно быть не менее 6 и не более 50 символов")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Email
    @NotBlank(message = "email не может быть пустым")
    @Length(min = 6,
            max = 100,
            message = "email должно быть не менее 6 и не более 100 символов")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "password не может быть пустым")
    @Length(min = 6,
            max = 255,
            message = "password должно быть не менее 6 и не более 255 символов")
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
