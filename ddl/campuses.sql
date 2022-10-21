CREATE TABLE campuses
(
    id            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`        VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    address       VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    region_id     BIGINT UNSIGNED                         NOT NULL,
    phone_number  VARCHAR(50) UNIQUE COLLATE ascii_general_ci  NULL,
    email         VARCHAR(150) UNIQUE COLLATE ascii_general_ci NULL,
    created_by    BIGINT UNSIGNED DEFAULT 0,
    created_at    DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by   BIGINT UNSIGNED DEFAULT NULL,
    modified_at   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;