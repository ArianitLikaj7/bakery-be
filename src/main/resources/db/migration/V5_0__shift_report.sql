create table shift_report
(
    id bigserial not null
        constraint shift_report_pkey
            primary key,
    shift varchar not null ,
    product_id bigint not null constraint fk_products references products,
    bakery_id bigint not null constraint fk_bakery references bakeries ,
    produced_quantity integer not null ,
    left_quantity integer,
    daily_earnings numeric(19,2)
);