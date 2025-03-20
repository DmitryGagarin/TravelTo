create table attraction_discussion_image
(
    id serial not null,
    uuid varchar not null default gen_random_uuid(),
    discussion_id bigint not null default 0,
    image bytea
)