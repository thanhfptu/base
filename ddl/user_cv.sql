CREATE TABLE user_cv
(
    id                  BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    student_id          BIGINT UNSIGNED,
    file_name           TEXT NOT NULL,
    url                 TEXT NOT NULL,
    thumbnail_url       TEXT NOT NULL,
    status              TINYINT                               NOT NULL DEFAULT 0,
    enabled             TINYINT                               NOT NULL DEFAULT 1,
    created_by          BIGINT UNSIGNED                                       DEFAULT 0,
    created_at          DATETIME                                              DEFAULT CURRENT_TIMESTAMP,
    modified_by         BIGINT UNSIGNED                                       DEFAULT NULL,
    modified_at         DATETIME                                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;