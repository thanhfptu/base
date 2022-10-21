CREATE TABLE final_evaluation
(
    id                      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    start_date              DATETIME        DEFAULT CURRENT_TIMESTAMP,
    end_date                DATETIME        DEFAULT CURRENT_TIMESTAMP,
    duration                INT UNSIGNED                         NOT NULL,
    staff_code              VARCHAR(50) UNIQUE COLLATE ascii_general_ci  NULL,
    division                VARCHAR(150) COLLATE utf8mb4_unicode_ci NULL,
    comment                 TEXT NULL,
    allowance               VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    major_point             FLOAT UNSIGNED NOT NULL DEFAULT 0,
    soft_skill_point        FLOAT UNSIGNED NOT NULL DEFAULT 0,
    attitude_point          FLOAT UNSIGNED NOT NULL DEFAULT 0,
    final_point             FLOAT UNSIGNED NOT NULL DEFAULT 0,
    created_by              BIGINT UNSIGNED DEFAULT 0,
    created_at              DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by             BIGINT UNSIGNED DEFAULT NULL,
    modified_at             DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;