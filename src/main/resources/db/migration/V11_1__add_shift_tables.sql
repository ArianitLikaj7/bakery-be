CREATE TABLE shift_report_summaries (
                                        id BIGSERIAL PRIMARY KEY,
                                        report_date DATE NOT NULL,
                                        shift VARCHAR(50) NOT NULL,
                                        total_daily_earnings NUMERIC(19, 2) NOT NULL,
                                        created_by UUID NOT NULL
);

CREATE TABLE shift_report_product_summaries (
                                                id BIGSERIAL PRIMARY KEY,
                                                product_name VARCHAR(255) NOT NULL,
                                                produced_quantity INT NOT NULL,
                                                left_quantity INT NOT NULL,
                                                product_price NUMERIC(19, 2) NOT NULL,
                                                product_profit NUMERIC(19, 2) NOT NULL,
                                                shift_report_summary_id BIGINT NOT NULL,
                                                CONSTRAINT fk_shift_report_summary FOREIGN KEY (shift_report_summary_id)
                                                    REFERENCES shift_report_summaries(id) ON DELETE CASCADE
);
