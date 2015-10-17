ALTER TABLE shipping_order ADD COLUMN shipper_id int unsigned DEFAULT NULL;
ALTER TABLE shipping_order ADD COLUMN price DECIMAL DEFAULT 0;