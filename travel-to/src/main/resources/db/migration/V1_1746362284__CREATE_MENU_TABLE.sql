create table menu
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid(),
    attraction_id bigint not null default 0
)