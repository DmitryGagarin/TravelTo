create table attraction_image
(
    id serial not null,
    uuid varchar not null default gen_random_uuid(),
    attraction_id bigint not null default 0,
    image bytea
)