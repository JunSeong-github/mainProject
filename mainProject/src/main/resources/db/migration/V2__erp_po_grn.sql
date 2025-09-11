-- 구매발주 헤더
create table if not exists purchase_order (
                                              id            bigint primary key auto_increment,
                                              po_no         varchar(50) not null unique,
    bp_name       varchar(200),                    -- 공급사명(간단화)
    status        varchar(20) not null,            -- DRAFT, APPROVED, CLOSED
    order_date    date not null,
    remark        varchar(500),
    created_at    timestamp default current_timestamp
    );

-- 구매발주 라인
create table if not exists po_line (
                                       id            bigint primary key auto_increment,
                                       po_id         bigint not null,
                                       item_id       bigint not null,
                                       qty           decimal(18,6) not null,
    unit_price    decimal(18,6) not null,
    amount        decimal(18,6) not null,
    foreign key (po_id) references purchase_order(id),
    foreign key (item_id) references item(id)
    );

-- 입고 헤더 (GRN)
create table if not exists grn (
                                   id            bigint primary key auto_increment,
                                   po_id         bigint not null,
                                   warehouse_id  bigint not null,
                                   received_at   timestamp not null,
                                   remark        varchar(500),
    created_at    timestamp default current_timestamp,
    foreign key (po_id) references purchase_order(id),
    foreign key (warehouse_id) references warehouse(id)
    );

-- 입고 라인
create table if not exists grn_line (
                                        id            bigint primary key auto_increment,
                                        grn_id        bigint not null,
                                        po_line_id    bigint not null,                 -- 어느 PO 라인을 입고하는지
                                        recv_qty      decimal(18,6) not null,
    recv_price    decimal(18,6) not null,          -- 입고단가(평균단가 갱신에 사용)
    foreign key (grn_id) references grn(id),
    foreign key (po_line_id) references po_line(id)
    );
