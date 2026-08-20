package ru.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.media.model.FriendRequest;

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
    
}
