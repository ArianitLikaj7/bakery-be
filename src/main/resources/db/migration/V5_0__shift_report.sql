create table shift_report
(
    id bigserial not null
        constraint shift_report_pkey
            primary key,
    shift varchar not null ,
    product_id bigint not null ,
    bakery_id bigint not null ,
    produced_quantity integer not null ,
    left_quantity integer,
    daily_earnings numeric(19,2)
);