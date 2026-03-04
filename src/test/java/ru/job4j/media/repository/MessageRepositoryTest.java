package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    public void whenSaveMessage_thenFindById() {
        var message = new Message();
        message.setSenderId(1L);
        message.setReceiverId(2L);
        message.setText("Hello");
        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);
        var foundMess = messageRepository.findById(message.getId());
        assertThat(foundMess).isPresent();
    }

    @Test
    public void whenFindAll_thenReturnMessage() {
        var message = new Message();
        message.setSenderId(1L);
        message.setReceiverId(2L);
        message.setText("Hello");
        message.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message);
        var message1 = new Message();
        message1.setSenderId(1L);
        message1.setReceiverId(2L);
        message1.setText("Bonsoir");
        message1.setCreatedAt(LocalDateTime.now());
        messageRepository.save(message1);
        var messages = messageRepository.findAll();
        assertThat(messages).hasSize(2);
    }

}