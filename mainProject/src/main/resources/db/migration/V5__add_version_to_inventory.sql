-- inventory.version 컬럼이 없을 때만 추가 (MySQL/MariaDB 호환)
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME   = 'inventory'
    AND COLUMN_NAME  = 'version'
);

SET @ddl := IF(
  @col_exists = 0,
  'ALTER TABLE `inventory` ADD COLUMN `version` BIGINT NOT NULL DEFAULT 0 COMMENT ''Optimistic Lock version''',
  'DO 0'
);

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
