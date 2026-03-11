package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    void deleteByUserFollowerAndUserFollowed(Long userFollower, Long userFollowed);
}
