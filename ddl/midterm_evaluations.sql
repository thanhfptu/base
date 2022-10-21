CREATE TABLE midterm_evaluation
(
    id            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    student_id    BIGINT UNSIGNED NOT NULL,
    company_contact_id    BIGINT UNSIGNED NOT NULL,
    semester_id   BIGINT NOT NULL,
    start_date    DATETIME        DEFAULT CURRENT_TIMESTAMP,
    end_date      DATETIME        DEFAULT CURRENT_TIMESTAMP,
    duration      INT UNSIGNED                         NOT NULL,
    staff_code    VARCHAR(50) UNIQUE COLLATE ascii_general_ci  NULL,
    division      VARCHAR(150) COLLATE utf8mb4_unicode_ci NULL,
    comment       TINYINT UNSIGNED NOT NULL,
    note          VARCHAR(255) COLLATE utf8mb4_unicode_ci,
    created_by    BIGINT UNSIGNED DEFAULT 0,
    created_at    DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by   BIGINT UNSIGNED DEFAULT NULL,
    modified_at   DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;