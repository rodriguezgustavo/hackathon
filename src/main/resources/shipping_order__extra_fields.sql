ALTER TABLE shipping_order ADD COLUMN shipper_id int unsigned DEFAULT NULL;
ALTER TABLE shipping_order ADD COLUMN shipping_price DECIMAL DEFAULT 0;
