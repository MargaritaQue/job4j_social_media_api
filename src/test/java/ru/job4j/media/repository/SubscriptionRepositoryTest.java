package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.Subscription;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    public void setUp() {
        subscriptionRepository.deleteAll();
    }

    @Test
    public void whenSaveSub_thenFindById() {
        var sub = new Subscription();
        sub.setUserFollower(1L);
        sub.setUserFollower(2L);
        subscriptionRepository.save(sub);
        var foundSub = subscriptionRepository.findById(sub.getId());
        assertThat(foundSub).isPresent();
    }

    @Test
    public void whenFindAll_thenReturnSubs() {
        var sub1 = new Subscription();
        sub1.setUserFollower(1L);
        sub1.setUserFollower(2L);
        var sub2 = new Subscription();
        sub2.setUserFollower(3L);
        sub2.setUserFollower(4L);
        subscriptionRepository.save(sub1);
        subscriptionRepository.save(sub2);
        var subs = subscriptionRepository.findAll();
        assertThat(subs).hasSize(2);
    }
}