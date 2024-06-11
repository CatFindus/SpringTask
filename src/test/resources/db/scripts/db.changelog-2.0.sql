--liquibase formatted sql
--changeset apuchinec:1
insert into companies(name)
values ('Apple'),
       ('Google'),
       ('Gazprom');

insert into users (user_name, firstname, lastname, password, birth_date, company_id)
values ('user1', 'fn_user1', 'ln_user1', 'pass_user1', '10.10.2010', 1),
       ('user2', 'fn_user2', 'ln_user2', 'pass_user2', '10.10.2010', 1),
       ('user3', 'fn_user3', 'ln_user3', 'pass_user3', '10.10.2010', 1);

insert into tags(name, color, type, created_at, updated_at, company_id)
values ('test_tag1', '#007bff', 'BUGS', now(), now(), 1),
       ('test_tag2', '#010bff', 'BUGS', now(), now(), 1),
       ('test_tag3', '#011bff', 'BUGS', now(), now(), 1);

insert into tasks (description, content, user_id, created_at, updated_at, task_status, tag_id, created_by)
VALUES ('task1_description', 'task1_content', 1, now(), now(), 'NOT_BEGINNED', 1, 1),
       ('task2_description', 'task2_content', 1, now(), now(), 'NOT_BEGINNED', 2, 2),
       ('task3_description', 'task3_content', 1, now(), now(), 'NOT_BEGINNED', 3, 3);

insert into comments(title, content, created_at, updated_at, user_id, task_id)
VALUES ('commentTitle1', 'commentContent1', now(), now(), 1, 1),
       ('commentTitle2', 'commentContent2', now(), now(), 2, 1),
       ('commentTitle3', 'commentContent3', now(), now(), 3, 1);