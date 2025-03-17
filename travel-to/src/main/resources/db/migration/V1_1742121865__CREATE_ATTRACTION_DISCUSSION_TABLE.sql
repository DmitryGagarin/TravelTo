create table attraction_discussion
(
    id serial not null,
    uuid varchar not null default gen_random_uuid(),
    content_like varchar not null default '',
    content_dislike varchar not null default '',
    content varchar not null default '',
    rating real not null default 0.0,
    title varchar not null default '',
    author_id bigint not null,
    constraint fk_user foreign key (author_id) references "users" (id),
    attraction_id bigint not null default 0
)