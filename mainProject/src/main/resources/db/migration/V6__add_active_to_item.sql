-- item.active 컬럼이 없을 때만 추가
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME   = 'item'
    AND COLUMN_NAME  = 'active'
);

SET @ddl := IF(
  @col_exists = 0,
  'ALTER TABLE `item` ADD COLUMN `active` BIT(1) NOT NULL DEFAULT b''1'' COMMENT ''Active flag''',
  'DO 0'
);

PREPARE stmt FROM @ddl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
