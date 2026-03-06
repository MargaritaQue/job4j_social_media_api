package ru.job4j.media.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.media.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
            SELECT user FROM User AS user
            WHERE user.username = :username AND user.password = :password
            """)
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("""
            SELECT user FROM User user WHERE user.id IN
            (SELECT sub.userFollower FROM Subscription sub WHERE sub.userFollowed = :userId)
            """)
    List<User> userSubscriptions(@Param("userId") Long userId);

    @Query("""
            SELECT user FROM User user WHERE user.id IN
            (SELECT f.friendId FROM Friendship f WHERE f.userId = :userId) OR user.id IN
            (SELECT f.userId FROM Friendship f WHERE f.friendId = :userId)
            """)
    List<User> userFriends(@Param("userId") Long userId);
}
