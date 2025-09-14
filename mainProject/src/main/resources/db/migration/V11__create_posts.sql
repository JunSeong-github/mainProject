-- posts 테이블이 없으면 생성
SET @tbl_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.TABLES
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'posts'
);

SET @ddl := IF(
  @tbl_exists = 0,
  'CREATE TABLE `posts` (
     `id`            BIGINT NOT NULL AUTO_INCREMENT,
     `user_id`       BIGINT NOT NULL,
     `title`         VARCHAR(200) NOT NULL,
     `content`       LONGTEXT NOT NULL,
     `slug`          VARCHAR(220) NULL,
     `published`     BIT(1) NOT NULL DEFAULT b''1'',
     `like_count`    INT NOT NULL DEFAULT 0,
     `comment_count` INT NOT NULL DEFAULT 0,
     `created_at`    DATETIME(6) NOT NULL,
     `updated_at`    DATETIME(6) NOT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4',
  'DO 0'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 유니크 인덱스(slug) 없으면 추가
SET @idx := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'posts'
    AND INDEX_NAME = 'uq_posts_slug'
    AND NON_UNIQUE = 0
);
SET @ddl := IF(
  @idx = 0,
  'CREATE UNIQUE INDEX `uq_posts_slug` ON `posts` (`slug`)',
  'DO 0'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 복합 인덱스(user_id, created_at) 없으면 추가
SET @idx := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'posts'
    AND INDEX_NAME = 'idx_posts_user_created'
);
SET @ddl := IF(
  @idx = 0,
  'CREATE INDEX `idx_posts_user_created` ON `posts` (`user_id`, `created_at`)',
  'DO 0'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- 제목 인덱스(title) 없으면 추가
SET @idx := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'posts'
    AND INDEX_NAME = 'idx_posts_title'
);
SET @ddl := IF(
  @idx = 0,
  'CREATE INDEX `idx_posts_title` ON `posts` (`title`)',
  'DO 0'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
