-- V3__seed_data.sql (idempotent upsert)

-- item
INSERT INTO item (code, name)
VALUES
    ('ITM-001','Sample Item A'),
    ('ITM-002','Sample Item B')
    AS new
ON DUPLICATE KEY UPDATE
                     name = new.name;

-- warehouse
INSERT INTO warehouse (code, name)
VALUES
    ('WH-SEO','Seoul WH'),
    ('WH-BUS','Busan WH')
    AS new
ON DUPLICATE KEY UPDATE
                     name = new.name;
