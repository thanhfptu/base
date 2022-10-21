CREATE TABLE ojt_information_registration
(
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    company_id  BIGINT UNSIGNED,
    position    VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    student_id  BIGINT NOT NULL ,
    semester_id BIGINT NOT NULL ,
    company_contact_id  BIGINT UNSIGNED,
    add_company_request_id BIGINT UNSIGNED,
    add_company_contact_request_id BIGINT UNSIGNED,
    status TINYINT NOT NULL DEFAULT 0,
    created_by  BIGINT UNSIGNED DEFAULT 0,
    created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    modified_by BIGINT UNSIGNED DEFAULT NULL,
    modified_at DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;