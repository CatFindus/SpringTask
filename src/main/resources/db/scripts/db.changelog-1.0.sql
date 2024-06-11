--liquibase formatted sql
--changeset apuchinec:1
drop table if exists tags;
drop table if exists comments;
drop table if exists tasks;
drop table if exists users;
drop table if exists companies;

CREATE TABLE if not exists companies
(
    id   serial PRIMARY KEY,
    name varchar(128) unique not null
);
--changeset apuchinec:2
CREATE TABLE if not exists users
(
    id         bigserial PRIMARY KEY,
    user_name  varchar(64)  not null unique,
    firstname  varchar(64),
    lastname   varchar(64),
    password   varchar(128) not null,
    created_at timestamp,
    updated_at timestamp,
    birth_date timestamp,
    company_id int          not null references companies (id)
);
--changeset apuchinec:3
CREATE TABLE if not exists tags
(
    id         bigserial PRIMARY KEY,
    name       varchar(24) not null,
    color      varchar(24) not null,
    type       varchar(24) not null,
    created_at timestamp,
    updated_at timestamp,
    company_id int references companies (id),
    unique (name, company_id)
);
--changeset apuchinec:4
CREATE TABLE if not exists tasks
(
    id          bigserial PRIMARY KEY,
    description varchar(255) not null,
    content     text,
    user_id     bigint       not null references users (id),
    created_at  timestamp,
    updated_at  timestamp,
    task_status varchar(20),
    tag_id      bigint references tags (id) ON DELETE set null,
    created_by  bigint       not null references users (id)
);

--changeset apuchinec:5
CREATE TABLE if not exists comments
(
    id         bigserial PRIMARY KEY,
    title      varchar(64) not null,
    content    varchar(1024),
    created_at timestamp,
    updated_at timestamp,
    user_id    bigint references users (id),
    task_id bigint references tasks(id)
);