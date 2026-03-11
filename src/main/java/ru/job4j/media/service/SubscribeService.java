package ru.job4j.media.service;

import org.springframework.transaction.annotation.Transactional;

public interface SubscribeService {

    @Transactional
    void sendFriendRequest(Long userId, Long friendId);

    @Transactional
    void acceptFriendRequest(Long requestId);

    @Transactional
    void rejectFriendRequest(Long requestId);

    @Transactional
    void deleteFriend(Long userId, Long friendId);

}
