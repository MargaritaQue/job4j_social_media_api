package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.FriendRequest;
import ru.job4j.media.model.FriendRequestStatus;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRequestRepositoryTest {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @BeforeEach
    public void setUp() {
        friendRequestRepository.deleteAll();
    }

    @Test
    public void whenSaveReq_thenFindById() {
        var friendReq = new FriendRequest();
        friendReq.setUserId(1L);
        friendReq.setFriendId(2L);
        friendReq.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(friendReq);
        var foundFriendReq = friendRequestRepository.findById(friendReq.getId());
        assertThat(foundFriendReq).isPresent();
    }

    @Test
    public void whenFindAll_thenReturnReq() {
        var friendReq = new FriendRequest();
        friendReq.setUserId(1L);
        friendReq.setFriendId(2L);
        friendReq.setStatus(FriendRequestStatus.PENDING);
        var friendReq1 = new FriendRequest();
        friendReq1.setUserId(3L);
        friendReq1.setFriendId(4L);
        friendReq1.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(friendReq);
        friendRequestRepository.save(friendReq1);
        var reqs = friendRequestRepository.findAll();
        assertThat(reqs).hasSize(2);
    }

}