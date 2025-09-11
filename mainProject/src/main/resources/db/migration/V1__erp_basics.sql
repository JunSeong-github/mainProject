-- 품목
create table if not exists item (
                                    id           bigint primary key auto_increment,
                                    code         varchar(50)  not null unique,
    name         varchar(200) not null,
    uom          varchar(20),
    tax_rate     decimal(5,2) default 0,
    is_active    boolean      default true
    );

-- 창고
create table if not exists warehouse (
                                         id           bigint primary key auto_increment,
                                         code         varchar(50)  not null unique,
    name         varchar(200) not null
    );

-- 재고 (품목 x 창고 1행)
create table if not exists inventory (
                                         id            bigint primary key auto_increment,
                                         item_id       bigint not null,
                                         warehouse_id  bigint not null,
                                         qty_on_hand   decimal(18,6) default 0,
    avg_cost      decimal(18,6) default 0,
    constraint uq_inventory_item_wh unique(item_id, warehouse_id),
    constraint fk_inventory_item foreign key (item_id) references item(id),
    constraint fk_inventory_wh   foreign key (warehouse_id) references warehouse(id)
    );
