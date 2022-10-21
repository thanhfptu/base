CREATE TABLE evaluation
(
    id                      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    student_id              BIGINT UNSIGNED NOT NULL,
    company_contact_id     BIGINT UNSIGNED NOT NULL,
    semester_id             BIGINT NOT NULL ,
    midterm_evaluation_id    BIGINT UNSIGNED NOT NULL,
    midterm_evaluation_status TINYINT NOT NULL DEFAULT -1,
    final_evaluation_id     BIGINT UNSIGNED NOT NULL,
    final_evaluation_status TINYINT NOT NULL DEFAULT -1,
    created_by              BIGINT UNSIGNED DEFAULT 0,
    created_at              DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by             BIGINT UNSIGNED DEFAULT NULL,
    modified_at             DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;