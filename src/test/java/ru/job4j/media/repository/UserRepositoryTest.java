package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUser_thenFindById() {
        var user = new User();
        user.setUsername("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("abcd");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        var foundPerson = userRepository.findById(user.getId());
        assertThat(foundPerson).isPresent();
        assertThat(foundPerson.get().getUsername()).isEqualTo("John Doe");
    }

    @Test
    public void whenFindAll_thenReturnAllPersons() {
        var user1 = new User();
        user1.setUsername("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("abcd");
        user1.setCreatedAt(LocalDateTime.now());
        var user2 = new User();
        user2.setUsername("Unos Aarg");
        user2.setEmail("unos.aarg@example.com");
        user2.setPassword("abcd");
        user2.setCreatedAt(LocalDateTime.now());
        userRepository.save(user1);
        userRepository.save(user2);
        var persons = userRepository.findAll();
        assertThat(persons).hasSize(2);
        assertThat(persons).extracting(User::getUsername).contains("John Doe", "Unos Aarg");
    }

}