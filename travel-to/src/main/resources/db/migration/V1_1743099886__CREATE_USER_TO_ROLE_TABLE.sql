create table user_to_role
(
    id         serial    primary key not null,
    uuid       varchar               not null     default gen_random_uuid(),
    user_id    bigint                not null     default 0,
    role       varchar               not null     default 'USER'
)