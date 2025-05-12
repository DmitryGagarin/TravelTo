create table theater_poster
(
    id serial not null,
    uuid varchar not null default gen_random_uuid(),
    image bytea not null,
    image_format varchar not null default '',
    attraction_id bigint not null default 0
)