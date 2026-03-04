package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
}
