CREATE TABLE companies
(
    id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name`          VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    address         VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description`   VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    img_url         VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    tax_code        VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
    website         VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    enabled         TINYINT UNSIGNED NOT NULL DEFAULT 1,
    created_by      BIGINT UNSIGNED DEFAULT 0,
    created_at      DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by     BIGINT UNSIGNED DEFAULT NULL,
    modified_at     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;

CREATE INDEX tax_code ON companies(tax_code)