create table likes
(
    id serial primary key not null,
    uuid varchar not null default gen_random_uuid(),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    user_id bigint not null default 0,
    attraction_id bigint not null default 0
)