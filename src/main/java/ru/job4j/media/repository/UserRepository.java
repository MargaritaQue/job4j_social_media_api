package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
