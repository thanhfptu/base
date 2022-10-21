CREATE TABLE add_company_request
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    offer_url   TEXT not null,
    mou_url     TEXT not null,
    tax_code    VARCHAR(14) COLLATE ascii_general_ci NOT NULL,
    `name`      VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    address     VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    website     VARCHAR(255) COLLATE utf8mb4_unicode_ci NULL,
    status TINYINT NOT NULL DEFAULT 0,
    created_by  BIGINT UNSIGNED DEFAULT 0,
    created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by BIGINT UNSIGNED DEFAULT NULL,
    modified_at DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;