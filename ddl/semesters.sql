CREATE TABLE semesters
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`      VARCHAR(50) COLLATE utf8mb4_unicode_ci NULL,
    `year`      INT UNSIGNED    DEFAULT (YEAR(CURRENT_TIMESTAMP)),
    start_date  DATE                                   NOT NULL,
    end_date    DATE                                   NOT NULL,
    created_by  BIGINT UNSIGNED DEFAULT 0,
    created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by BIGINT UNSIGNED DEFAULT NULL,
    modified_at DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;