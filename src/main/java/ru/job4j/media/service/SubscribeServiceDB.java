package ru.job4j.media.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.media.model.FriendRequest;
import ru.job4j.media.model.FriendRequestStatus;
import ru.job4j.media.model.Friendship;
import ru.job4j.media.model.Subscription;
import ru.job4j.media.repository.FriendRequestRepository;
import ru.job4j.media.repository.FriendshipRepository;
import ru.job4j.media.repository.SubscriptionRepository;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class SubscribeServiceDB implements SubscribeService {
    private final SubscriptionRepository subscriptionRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    @Transactional
    public void sendFriendRequest(Long userId, Long friendId) {
        friendRequestRepository.save(new FriendRequest(null, userId, friendId, FriendRequestStatus.PENDING));
        subscriptionRepository.save(new Subscription(null, userId, friendId));
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long requestId) {
        FriendRequest fr = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Request not found"));
        if (fr.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("Request is not pending");
        }

        Long fromUserId = fr.getUserId();
        Long toUserId = fr.getFriendId();

        fr.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(fr);

        subscriptionRepository.save(new Subscription(null, toUserId, fromUserId));

        friendshipRepository.save(new Friendship(null, fromUserId, toUserId));
        friendshipRepository.save(new Friendship(null, toUserId, fromUserId));
    }

    @Override
    public void rejectFriendRequest(Long requestId) {
        FriendRequest fr = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Request not found"));
        if (fr.getStatus() != FriendRequestStatus.PENDING) {
            throw new IllegalArgumentException("Request is not pending");
        }
        fr.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(fr);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        friendshipRepository.deleteByUserIdAndFriendId(userId, friendId);
        friendshipRepository.deleteByUserIdAndFriendId(friendId, userId);
        subscriptionRepository.deleteByUserFollowerAndUserFollowed(userId, friendId);
    }
}
