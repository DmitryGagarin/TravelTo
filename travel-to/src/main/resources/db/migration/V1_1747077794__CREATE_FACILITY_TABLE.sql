create table facility
(
    id serial not null,
    uuid varchar not null default gen_random_uuid(),
    attraction_id bigint not null default 0,
    name varchar not null default '',
    description varchar not null default '',
    image bytea,
    image_format varchar,
    open_time varchar not null default '',
    close_time varchar not null default ''
)