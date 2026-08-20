package ru.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.media.model.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

    void deleteByUserIdAndFriendId(Long userId, Long friendId);
}
