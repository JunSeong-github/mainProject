-- === inventory.version 없으면 추가 (낙관적 락) ===
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
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- === item.active 없으면 추가 (Hibernate boolean -> BIT(1) 기본 매핑) ===
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
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

-- === item.tax_rate 없으면 추가 (퍼센트 저장: 0.00 ~ 100.00) ===
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME   = 'item'
    AND COLUMN_NAME  = 'tax_rate'
);
SET @ddl := IF(
  @col_exists = 0,
  'ALTER TABLE `item` ADD COLUMN `tax_rate` DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT ''Tax rate percent''',
  'DO 0'
);
PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
