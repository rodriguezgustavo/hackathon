USE meli_hackathon_db;
ALTER TABLE shipper ADD COLUMN last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE shipper ADD COLUMN token VARCHAR(1024) DEFAULT NULL;
