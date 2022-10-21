CREATE TABLE jobs
(
    id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title           VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description`   VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    requirement     VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    benefit         VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    num_of_recruit  TINYINT UNSIGNED DEFAULT 0,
    num_of_applied  TINYINT UNSIGNED DEFAULT 0,
    company_id      BIGINT UNSIGNED NOT NULL,
    semester_id     BIGINT UNSIGNED NOT NULL,
    region_id       BIGINT UNSIGNED NOT NULL,
    publish_date    DATETIME DEFAULT CURRENT_TIMESTAMP,
    expired_date    DATETIME,
    salary          VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    img_url         VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    is_visible      TINYINT                                 NOT NULL DEFAULT 1,
    is_active       TINYINT                                 NOT NULL DEFAULT 1,
    is_featured     TINYINT                                 NOT NULL DEFAULT 1,
    created_by      BIGINT UNSIGNED DEFAULT 0,
    created_at      DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by     BIGINT UNSIGNED DEFAULT NULL,
    modified_at     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;