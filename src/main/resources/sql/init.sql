CREATE TABLE companies (
    id serial PRIMARY KEY,
    name varchar(128) unique not null
);

CREATE TABLE users(
    id bigserial PRIMARY KEY,
    user_name varchar(64) not null  unique,
    firstname varchar(64),
    lastname varchar(64),
    password varchar(128) not null,
    birth_date timestamp,
    company_id int not null references companies(id)
);

CREATE TABLE tasks (
    id bigserial PRIMARY KEY,
    description varchar(255) not null ,
    content text,
    user_id bigint not null references users(id),
    created_by bigint not null references users(id)
);