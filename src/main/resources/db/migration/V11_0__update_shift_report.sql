DROP TABLE IF EXISTS shift_report CASCADE;

CREATE TABLE shift_report (
                              id BIGSERIAL PRIMARY KEY,
                              report_date DATE NOT NULL DEFAULT CURRENT_DATE,
                              shift VARCHAR NOT NULL,
                              bakery_id BIGINT NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (bakery_id) REFERENCES bakeries(id) ON DELETE CASCADE
);

CREATE TABLE shift_report_products (
                                       id BIGSERIAL PRIMARY KEY,
                                       shift_report_id BIGINT NOT NULL,
                                       product_id BIGINT NOT NULL,
                                       produced_quantity INT DEFAULT 0,
                                       left_quantity INT DEFAULT 0,
                                       daily_earnings DECIMAL(19,2) DEFAULT 0.00,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       FOREIGN KEY (shift_report_id) REFERENCES shift_report (id) ON DELETE CASCADE,
                                       FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
