CREATE TABLE cv_comment
(
    id             BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    author_id      BIGINT UNSIGNED NOT NULL,
    cv_id          BIGINT UNSIGNED NOT NULL,
    content        VARCHAR(255) NOT NULL,
    is_resolved    TINYINT UNSIGNED NOT NULL DEFAULT 0,
    is_deleted     TINYINT UNSIGNED NOT NULL DEFAULT 0,
    created_by     BIGINT UNSIGNED                              DEFAULT 0,
    created_at     DATETIME                                     DEFAULT CURRENT_TIMESTAMP,
    modified_by    BIGINT UNSIGNED                              DEFAULT NULL,
    modified_at    DATETIME                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;

CREATE INDEX cv_id ON cv_comment(cv_id)