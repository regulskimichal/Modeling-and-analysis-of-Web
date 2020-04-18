create table api_settings
(
    api_name text not null primary key,
    api_key  text
);

insert into api_settings
values ('WEB_PAGE_TEST', null),
       ('PAGE_SPEED', null);

create table measurement_settings
(
    id                 bigserial not null primary key,
    page_url            text      not null,
    cron_expression     text      not null,
    json_path_expression text
);

create type result_type as enum ('SUCCESS', 'WITH_ERRORS', 'FAILURE');
create type strategy as enum ('DESKTOP', 'MOBILE');

create table measurements
(
    id            bigserial   not null primary key,
    analysis_time timestamp   not null,
    url           decimal     not null,
    result_type   result_type not null,
    strategy      strategy    not null,
    user_agent    text
);
