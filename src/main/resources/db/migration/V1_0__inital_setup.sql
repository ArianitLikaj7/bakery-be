create table users
(
    id         UUID not null
        constraint user_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    first_name varchar,
    last_name varchar,
    email varchar,
    password varchar,
    role varchar
);

create table tenants
(
    id bigserial not null
                    constraint tenant_pkey
                        primary key ,
    created_at timestamp,
    updated_at timestamp,
    business_name varchar,
    identification_number varchar,
    tenant_owner_user_id UUID
);

alter table users add column tenant_id bigint constraint fk_tenant references tenants;

create table bakeries
(
    id         bigserial not null
        constraint bakery_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    tenant_id bigint constraint fk_tenant references tenants
);

create table products
(
    id         bigserial not null
        constraint product_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    name varchar,
    price numeric(19, 2),
    bakery_id bigint constraint fk_bakery references bakeries
);

insert into users(id, created_at, first_name, last_name, email, password, role)
values ('6de9c28c-f2b2-42d3-8db2-3146b52346e0',
        current_timestamp,
        'Qendrim',
        'Zeneli',
        'qendrimzeneli195@gmail.com',
        '$2a$10$Cz9xGfJtHLAPEZIHsHQat.udW9TdYwHKlqP9OZT9dSpiThlzGN2dS',
        'SUPER_ADMIN');