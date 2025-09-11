-- 기본 마스터
CREATE TABLE IF NOT EXISTS item (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS warehouse (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS inventory (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         item_id BIGINT NOT NULL,
                                         warehouse_id BIGINT NOT NULL,
                                         qty_on_hand DECIMAL(18,6) NOT NULL DEFAULT 0,
    avg_cost DECIMAL(18,6) NOT NULL DEFAULT 0,
    UNIQUE KEY uk_inv_item_wh (item_id, warehouse_id),
    CONSTRAINT fk_inv_item FOREIGN KEY (item_id) REFERENCES item(id),
    CONSTRAINT fk_inv_wh   FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
    );

-- 구매
CREATE TABLE IF NOT EXISTS purchase_order (
                                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                              po_no VARCHAR(50) NOT NULL UNIQUE,
    bp_name VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    order_date DATE NOT NULL,
    remark VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS po_line (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       po_id BIGINT NOT NULL,
                                       item_id BIGINT NOT NULL,
                                       qty DECIMAL(18,6) NOT NULL,
    unit_price DECIMAL(18,6) NOT NULL,
    amount DECIMAL(18,6) NOT NULL,
    CONSTRAINT fk_pol_po   FOREIGN KEY (po_id)   REFERENCES purchase_order(id),
    CONSTRAINT fk_pol_item FOREIGN KEY (item_id) REFERENCES item(id)
    );

CREATE TABLE IF NOT EXISTS grn (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   po_id BIGINT NOT NULL,
                                   warehouse_id BIGINT NOT NULL,
                                   received_at DATETIME NOT NULL,
                                   remark VARCHAR(255),
    CONSTRAINT fk_grn_po FOREIGN KEY (po_id) REFERENCES purchase_order(id),
    CONSTRAINT fk_grn_wh FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
    );

CREATE TABLE IF NOT EXISTS grn_line (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        grn_id BIGINT NOT NULL,
                                        po_line_id BIGINT NOT NULL,
                                        recv_qty DECIMAL(18,6) NOT NULL,
    recv_price DECIMAL(18,6) NOT NULL,
    CONSTRAINT fk_grnl_grn   FOREIGN KEY (grn_id)    REFERENCES grn(id),
    CONSTRAINT fk_grnl_polin FOREIGN KEY (po_line_id) REFERENCES po_line(id)
    );

-- 판매
CREATE TABLE IF NOT EXISTS sales_order (
                                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                           so_no VARCHAR(50) NOT NULL UNIQUE,
    bp_name VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    order_date DATE NOT NULL,
    remark VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS so_line (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       so_id BIGINT NOT NULL,
                                       item_id BIGINT NOT NULL,
                                       qty DECIMAL(18,6) NOT NULL,
    unit_price DECIMAL(18,6) NOT NULL,
    amount DECIMAL(18,6) NOT NULL,
    CONSTRAINT fk_sol_so   FOREIGN KEY (so_id)   REFERENCES sales_order(id),
    CONSTRAINT fk_sol_item FOREIGN KEY (item_id) REFERENCES item(id)
    );

-- 출고
CREATE TABLE IF NOT EXISTS shipment (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        so_id BIGINT NOT NULL,
                                        warehouse_id BIGINT NOT NULL,
                                        shipped_at DATETIME NOT NULL,
                                        remark VARCHAR(255),
    CONSTRAINT fk_ship_so FOREIGN KEY (so_id) REFERENCES sales_order(id),
    CONSTRAINT fk_ship_wh FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)
    );

CREATE TABLE IF NOT EXISTS shipment_line (
                                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                             shipment_id BIGINT NOT NULL,
                                             so_line_id BIGINT NOT NULL,
                                             ship_qty DECIMAL(18,6) NOT NULL,
    CONSTRAINT fk_shipl_ship  FOREIGN KEY (shipment_id) REFERENCES shipment(id),
    CONSTRAINT fk_shipl_solin FOREIGN KEY (so_line_id)  REFERENCES so_line(id)
    );
