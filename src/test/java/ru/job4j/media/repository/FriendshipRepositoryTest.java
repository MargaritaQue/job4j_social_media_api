package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.Friendship;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @BeforeEach
    public void setUp() {
        friendshipRepository.deleteAll();
    }

    @Test
    public void whenSaveFriendship_thenFindById() {
        var friendship = new Friendship();
        friendship.setUserId(1L);
        friendship.setFriendId(2L);
        friendshipRepository.save(friendship);
        var foundFriendship = friendshipRepository.findById(friendship.getId());
        assertThat(foundFriendship).isPresent();
    }

    @Test
    public void whenFindAll_thenReturnFriendship() {
        var friendship = new Friendship();
        friendship.setUserId(1L);
        friendship.setFriendId(2L);
        var friendship1 = new Friendship();
        friendship1.setUserId(3L);
        friendship1.setFriendId(4L);
        friendshipRepository.save(friendship);
        friendshipRepository.save(friendship1);
        var friendships = friendshipRepository.findAll();
        assertThat(friendships).hasSize(2);
    }

}