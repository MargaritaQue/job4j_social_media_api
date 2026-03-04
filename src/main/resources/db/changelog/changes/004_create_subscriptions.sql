CREATE TABLE subscriptions (
    user_follower BIGINT NOT NULL,
    user_followed BIGINT NOT NULL,
    PRIMARY KEY (user_follower, user_followed),
    FOREIGN KEY (user_follower) REFERENCES users(id),
    FOREIGN KEY (user_followed) REFERENCES users(id)
);