package ru.media.repository;

import org.springframework.data.repository.CrudRepository;
import ru.media.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
