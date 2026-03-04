CREATE TABLE post_images (
    id BIGSERIAL PRIMARY KEY,
    post_id  BIGINT NOT NULL REFERENCES posts(id),
    url VARCHAR(500) NOT NULL
);