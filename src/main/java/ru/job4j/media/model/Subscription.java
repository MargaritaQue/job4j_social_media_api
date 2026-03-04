package ru.job4j.media.model;

import jakarta.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_follower")
    private Long userFollower;

    @Column(name = "user_followed")
    private Long userFollowed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(Long userFollower) {
        this.userFollower = userFollower;
    }

    public Long getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(Long userFollowed) {
        this.userFollowed = userFollowed;
    }
}
