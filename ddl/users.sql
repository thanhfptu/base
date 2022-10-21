CREATE TABLE users
(
    id             BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    full_name      VARCHAR(255) COLLATE utf8mb4_unicode_ci      NOT NULL,
    roll_number    VARCHAR(10) UNIQUE COLLATE ascii_general_ci  NULL,
    gender         TINYINT UNSIGNED                                      DEFAULT 0,
    date_of_birth  DATE                                         NULL,
    edu_email      VARCHAR(150) UNIQUE COLLATE ascii_general_ci NOT NULL,
    personal_email VARCHAR(150) UNIQUE COLLATE ascii_general_ci NULL,
    phone_number   VARCHAR(50) UNIQUE COLLATE ascii_general_ci  NULL,
    address        VARCHAR(255) COLLATE utf8mb4_unicode_ci      NULL,
    ojt_status     TINYINT                                               DEFAULT -1,
    major_id       BIGINT UNSIGNED                              NULL,
    campus_id      BIGINT UNSIGNED                              NULL,
    status         TINYINT                                       NOT NULL DEFAULT 1,
    created_by     BIGINT UNSIGNED                                       DEFAULT 0,
    created_at     DATETIME                                              DEFAULT CURRENT_TIMESTAMP,
    modified_by    BIGINT UNSIGNED                                       DEFAULT NULL,
    modified_at    DATETIME                                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;