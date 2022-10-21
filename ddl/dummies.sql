CREATE TABLE dummies
(
    id   BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL COLLATE utf8mb4_unicode_ci COMMENT 'Dummy name',
    created_by BIGINT UNSIGNED NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by BIGINT UNSIGNED NULL,
    modified_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;