CREATE TABLE import_histories
(
    id          bigint unsigned primary key auto_increment,
    file_name   varchar(255) collate utf8mb4_general_ci not null,
    source_url  text collate utf8mb4_general_ci,
    result_url  text collate utf8mb4_general_ci,
    type        tinyint                                 not null,
    `status`    tinyint                                 not null,
    message     varchar(255) collate utf8mb4_general_ci default null,
    created_by  BIGINT UNSIGNED                         DEFAULT 0,
    created_at  DATETIME                                DEFAULT CURRENT_TIMESTAMP,
    modified_by BIGINT UNSIGNED                         DEFAULT NULL,
    modified_at DATETIME                                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci AUTO_INCREMENT = 1;