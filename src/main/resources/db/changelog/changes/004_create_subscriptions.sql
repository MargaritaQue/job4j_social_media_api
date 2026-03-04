CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_follower BIGINT NOT NULL,
    user_followed BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_follower) REFERENCES users(id),
    FOREIGN KEY (user_followed) REFERENCES users(id),
    UNIQUE (user_follower, user_followed)
);