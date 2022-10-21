CREATE TABLE news
(
    id                  BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title               VARCHAR(150) UNIQUE COLLATE ascii_general_ci NOT NULL,
    content             TEXT                                         NOT NULL,
    is_visible          TINYINT                                      NOT NULL DEFAULT 1,
    is_featured         TINYINT                                      NOT NULL DEFAULT 1,
    thumbnail           VARCHAR(255) COLLATE ascii_general_ci NOT NULL,
    short_description   VARCHAR(255) COLLATE ascii_general_ci NOT NULL,
    publish_date        DATETIME                                              DEFAULT CURRENT_TIMESTAMP,
    created_by          BIGINT UNSIGNED                                       DEFAULT 0,
    created_at          DATETIME                                              DEFAULT CURRENT_TIMESTAMP,
    modified_by         BIGINT UNSIGNED                                       DEFAULT NULL,
    modified_at         DATETIME                                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;