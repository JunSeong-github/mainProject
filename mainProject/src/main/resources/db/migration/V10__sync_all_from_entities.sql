/**********************************************************
 * 한 방에 싱크: 엔티티 기준으로 부족 컬럼 추가/확장
 * - MySQL/MariaDB 공용
 * - 재실행 안전(존재하면 패스)
 **********************************************************/

/* ---------- ITEM ---------- */
/* uom (없으면 추가, 있고 길이<20이면 확장) */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='uom');
SET @ddl := IF(@col=0,
  'ALTER TABLE `item` ADD COLUMN `uom` VARCHAR(20) NULL COMMENT ''Unit of Measure''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

SET @len := (SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='uom');
SET @ddl := IF(@len IS NOT NULL AND @len < 20,
  'ALTER TABLE `item` MODIFY COLUMN `uom` VARCHAR(20) NULL COMMENT ''Unit of Measure''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* tax_rate (없으면 추가, precision/scale 다르면 수정) */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='tax_rate');
SET @ddl := IF(@col=0,
  'ALTER TABLE `item` ADD COLUMN `tax_rate` DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT ''Tax rate percent''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

SET @prec := (SELECT NUMERIC_PRECISION FROM INFORMATION_SCHEMA.COLUMNS
              WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='tax_rate');
SET @scale := (SELECT NUMERIC_SCALE FROM INFORMATION_SCHEMA.COLUMNS
              WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='tax_rate');
SET @ddl := IF(@prec IS NOT NULL AND (@prec < 5 OR @scale <> 2),
  'ALTER TABLE `item` MODIFY COLUMN `tax_rate` DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT ''Tax rate percent''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* active (없으면 추가) — Hibernate boolean 기본 매핑 BIT(1) */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='active');
SET @ddl := IF(@col=0,
  'ALTER TABLE `item` ADD COLUMN `active` BIT(1) NOT NULL DEFAULT b''1'' COMMENT ''Active flag''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* name 길이 확장: 100 -> 200 (필요 시) */
SET @len := (SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='item' AND COLUMN_NAME='name');
SET @ddl := IF(@len IS NOT NULL AND @len < 200,
  'ALTER TABLE `item` MODIFY COLUMN `name` VARCHAR(200) NOT NULL',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;


/* ---------- INVENTORY ---------- */
/* version (없으면 추가) */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='inventory' AND COLUMN_NAME='version');
SET @ddl := IF(@col=0,
  'ALTER TABLE `inventory` ADD COLUMN `version` BIGINT NOT NULL DEFAULT 0 COMMENT ''Optimistic Lock version''',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* qty_on_hand / avg_cost (없으면 추가) */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='inventory' AND COLUMN_NAME='qty_on_hand');
SET @ddl := IF(@col=0,
  'ALTER TABLE `inventory` ADD COLUMN `qty_on_hand` DECIMAL(18,6) NOT NULL DEFAULT 0',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='inventory' AND COLUMN_NAME='avg_cost');
SET @ddl := IF(@col=0,
  'ALTER TABLE `inventory` ADD COLUMN `avg_cost` DECIMAL(18,6) NOT NULL DEFAULT 0',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* unique (item_id, warehouse_id) 없으면 추가 */
SET @uk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
            WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='inventory'
              AND INDEX_NAME='uq_inventory_item_wh');
SET @ddl := IF(@uk=0,
  'ALTER TABLE `inventory` ADD CONSTRAINT `uq_inventory_item_wh` UNIQUE (`item_id`,`warehouse_id`)',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;


/* ---------- PURCHASE_ORDER / PO_LINE / GRN / GRN_LINE / SALES_ORDER / SO_LINE / SHIPMENT / SHIPMENT_LINE / WAREHOUSE ----------
   아래는 “있으면 패스” 패턴으로 최소 보강(없는 경우만 추가).
   필요시 더 엄격한 타입/길이 수정도 같은 방식으로 IF 조건 넣어 확장 가능.
*/

/* purchase_order.remark 500 확장(필요 시) */
SET @len := (SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='purchase_order' AND COLUMN_NAME='remark');
SET @ddl := IF(@len IS NOT NULL AND @len < 500,
  'ALTER TABLE `purchase_order` MODIFY COLUMN `remark` VARCHAR(500)',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* grn.remark 500 확장(필요 시) */
SET @len := (SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='grn' AND COLUMN_NAME='remark');
SET @ddl := IF(@len IS NOT NULL AND @len < 500,
  'ALTER TABLE `grn` MODIFY COLUMN `remark` VARCHAR(500)',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;

/* sales_order.status 길이 20 보장(필요 시) */
SET @len := (SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS
             WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='sales_order' AND COLUMN_NAME='status');
SET @ddl := IF(@len IS NOT NULL AND @len < 20,
  'ALTER TABLE `sales_order` MODIFY COLUMN `status` VARCHAR(20) NOT NULL',
  'DO 0'); PREPARE s FROM @ddl; EXECUTE s; DEALLOCATE PREPARE s;
