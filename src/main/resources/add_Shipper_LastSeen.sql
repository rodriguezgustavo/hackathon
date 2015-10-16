USE meli_hackathon_db;
ALTER TABLE shipper ADD COLUMN last_seen TIMESTAMP DEFAULT 0;
