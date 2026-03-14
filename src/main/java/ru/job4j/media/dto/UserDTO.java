package ru.job4j.media.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.media.model.Post;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "userid не может быть пустым")
    private Long userId;

    @NotBlank(message = "username не может быть пустым")
    @Length(min = 6,
            max = 10,
            message = "username должно быть не менее 6 и не более 10 символов")
    private String username;
    private List<Post> posts;

}
