package ru.job4j.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.media.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
