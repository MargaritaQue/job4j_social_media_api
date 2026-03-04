package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
}
